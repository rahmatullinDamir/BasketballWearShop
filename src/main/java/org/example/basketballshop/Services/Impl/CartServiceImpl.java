package org.example.basketballshop.Services.Impl;

import org.example.basketballshop.DTO.CartDto;
import org.example.basketballshop.DTO.CartItemDto;
import org.example.basketballshop.DTO.GiftCertificateDto;
import org.example.basketballshop.Models.*;
import org.example.basketballshop.Models.Enums.GiftCertificateStatus;
import org.example.basketballshop.Repositories.*;
import org.example.basketballshop.Services.BadgesService;
import org.example.basketballshop.Services.CartService;
import org.example.basketballshop.Services.ProductService;
import org.example.basketballshop.Services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CartServiceImpl implements CartService {
    private static final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private BadgesService badgesService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private GiftCertificateRepository giftCertificateRepository;

    public void addToCart(Long userId, Long productId, int quantity, String size) {
        try {
            logger.info("Adding product {} with size {} and quantity {} to cart for user {}", productId, size, quantity, userId);
            
            Cart cart = cartRepository.findByUserId(userId)
                    .orElseGet(() -> {
                        logger.info("Creating new cart for user {}", userId);
                        return createNewCartForUser(userId);
                    });

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> {
                        logger.error("Product not found with ID: {}", productId);
                        return new RuntimeException("Product not found");
                    });

            ProductSize productSize = product.getSizes().stream()
                    .filter(ps -> ps.getSize().equals(size))
                    .findFirst()
                    .orElseThrow(() -> {
                        logger.error("Size {} not found for product {}", size, productId);
                        return new IllegalArgumentException("Размер не найден: " + size);
                    });

            CartItem existingItem = cart.getItems().stream()
                    .filter(item -> item.getProduct().getId().equals(productId)
                            && item.getProductSizeId().equals(productSize.getId()))
                    .findFirst()
                    .orElse(null);

            if (existingItem != null) {
                logger.info("Updating quantity for existing cart item");
                existingItem.setQuantity(existingItem.getQuantity() + quantity);
                cartItemRepository.save(existingItem);
            } else {
                logger.info("Creating new cart item");
                CartItem newItem = new CartItem();
                newItem.setProduct(product);
                newItem.setProductSizeId(productSize.getId());
                newItem.setQuantity(quantity);
                newItem.setCart(cart);
                cart.getItems().add(newItem);
                cartRepository.save(cart);
            }
            logger.info("Successfully added/updated item in cart");
        } catch (Exception e) {
            logger.error("Error adding item to cart", e);
            throw e;
        }
    }

    public int getQuantityOfCart(Long userId) {
        try {
            logger.info("Getting cart quantity for user {}", userId);
            Cart cart = cartRepository.findByUserId(userId).orElse(new Cart());
            int quantity = cart.getItems().stream().mapToInt(CartItem::getQuantity).sum();
            logger.info("Cart quantity for user {}: {}", userId, quantity);
            return quantity;
        } catch (Exception e) {
            logger.error("Error getting cart quantity for user {}", userId, e);
            throw e;
        }
    }

    private Cart createNewCartForUser(Long userId) {
        try {
            logger.info("Creating new cart for user {}", userId);
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> {
                        logger.error("User not found with ID: {}", userId);
                        return new RuntimeException("User not found");
                    });
            Cart newCart = new Cart();
            newCart.setUser(user);
            Cart savedCart = cartRepository.save(newCart);
            logger.info("Successfully created new cart for user {}", userId);
            return savedCart;
        } catch (Exception e) {
            logger.error("Error creating new cart for user {}", userId, e);
            throw e;
        }
    }

    @Override
    public CartDto getCartWithDiscount(String userEmail) {
        try {
            logger.info("Getting cart with discount for user {}", userEmail);
            User user = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> {
                        logger.error("User not found with email: {}", userEmail);
                        return new RuntimeException("User not found");
                    });

            Cart cart = user.getCart();
            if (cart == null) {
                logger.info("Creating new cart for user {}", userEmail);
                cart = createNewCartForUser(user.getId());
                user.setCart(cart);
                userRepository.save(user);
            }

            int discountPercentage = badgesService.calculateDiscountByBadges();
            logger.info("Calculated discount percentage: {}%", discountPercentage);

            BigDecimal subtotal = cart.getItems().stream()
                    .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal discountAmount = subtotal.multiply(
                BigDecimal.valueOf(discountPercentage)
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)
            );
            BigDecimal afterDiscount = subtotal.subtract(discountAmount);

            BigDecimal total = calculateTotalWithCertificate(cart, afterDiscount);
            logger.info("Calculated total after discounts and certificate: {}", total);

            List<CartItem> discountItems = productService.getCartProductsWithDiscount(discountPercentage, cart.getItems());

            CartDto cartDto = CartDto.builder()
                    .items(CartItemDto.from(discountItems))
                    .total(total)
                    .discount(discountPercentage)
                    .appliedCertificate(GiftCertificateDto.from(cart.getAppliedCertificate()))
                    .build();

            logger.info("Successfully retrieved cart with discount for user {}", userEmail);
            return cartDto;
        } catch (Exception e) {
            logger.error("Error getting cart with discount for user {}", userEmail, e);
            throw e;
        }
    }

    @Override
    public void applyGiftCertificate(String code, String userEmail) {
        try {
            logger.info("Applying gift certificate {} for user {}", code, userEmail);
            User user = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> {
                        logger.error("User not found with email: {}", userEmail);
                        return new RuntimeException("Пользователь не найден");
                    });

            Cart cart = user.getCart();
            if (cart == null) {
                logger.error("Cart not found for user {}", userEmail);
                throw new RuntimeException("Корзина не найдена");
            }

            if (cart.getAppliedCertificate() != null) {
                logger.error("Certificate already applied to cart for user {}", userEmail);
                throw new RuntimeException("В корзине уже применен подарочный сертификат");
            }

            GiftCertificate certificate = giftCertificateRepository
                    .findByCodeAndStatus(code, GiftCertificateStatus.ACTIVE)
                    .orElseThrow(() -> {
                        logger.error("Certificate not found or already used: {}", code);
                        return new RuntimeException("Сертификат не найден или уже использован");
                    });

            if (certificate.getExpiresAt().isBefore(LocalDateTime.now())) {
                logger.error("Certificate {} has expired", code);
                certificate.setStatus(GiftCertificateStatus.EXPIRED);
                giftCertificateRepository.save(certificate);
                throw new RuntimeException("Срок действия сертификата истек");
            }

            certificate.setUsedBy(user);
            certificate.setUsedAt(LocalDateTime.now());
            
            cart.setAppliedCertificate(certificate);
            cartRepository.save(cart);
            logger.info("Successfully applied gift certificate {} for user {}", code, userEmail);
        } catch (Exception e) {
            logger.error("Error applying gift certificate {} for user {}", code, userEmail, e);
            throw e;
        }
    }

    @Override
    public void removeGiftCertificate(String userEmail) {
        try {
            logger.info("Removing gift certificate for user {}", userEmail);
            User user = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> {
                        logger.error("User not found with email: {}", userEmail);
                        return new RuntimeException("Пользователь не найден");
                    });

            Cart cart = user.getCart();
            if (cart == null) {
                logger.error("Cart not found for user {}", userEmail);
                throw new RuntimeException("Корзина не найдена");
            }

            cart.setAppliedCertificate(null);
            cartRepository.save(cart);
            logger.info("Successfully removed gift certificate for user {}", userEmail);
        } catch (Exception e) {
            logger.error("Error removing gift certificate for user {}", userEmail, e);
            throw e;
        }
    }

    @Override
    public BigDecimal calculateTotalWithCertificate(Cart cart, BigDecimal subtotal) {
        try {
            logger.info("Calculating total with certificate for cart");
            if (cart.getAppliedCertificate() != null) {
                BigDecimal certificateAmount = cart.getAppliedCertificate().getAmount();
                logger.info("Certificate amount: {}", certificateAmount);
                
                if (subtotal.compareTo(certificateAmount) > 0) {
                    BigDecimal total = subtotal.subtract(certificateAmount).setScale(2, RoundingMode.HALF_UP);
                    logger.info("Total after applying certificate: {}", total);
                    return total;
                } else {
                    logger.info("Certificate amount exceeds subtotal, returning zero");
                    return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
                }
            }
            logger.info("No certificate applied, returning subtotal: {}", subtotal);
            return subtotal.setScale(2, RoundingMode.HALF_UP);
        } catch (Exception e) {
            logger.error("Error calculating total with certificate", e);
            throw e;
        }
    }

    @Override
    public Cart updateQuantity(Long itemId, int quantity) {
        try {
            logger.info("Updating quantity to {} for cart item {}", quantity, itemId);
            CartItem item = cartItemRepository.findById(itemId)
                    .orElseThrow(() -> {
                        logger.error("Cart item not found with ID: {}", itemId);
                        return new RuntimeException("Item not found");
                    });
            item.setQuantity(quantity);
            cartItemRepository.save(item);
            logger.info("Successfully updated quantity for cart item {}", itemId);
            return item.getCart();
        } catch (Exception e) {
            logger.error("Error updating quantity for cart item {}", itemId, e);
            throw e;
        }
    }

    @Override
    public void removeItem(Long itemId) {
        try {
            logger.info("Removing item {} from cart", itemId);
            User user = userService.getUserFromSession();
            
            Optional<Cart> cart = cartRepository.findByUserId(user.getId());
            if (cart.isEmpty()) {
                logger.error("Cart not found for user {}", user.getId());
                throw new RuntimeException("Корзина не найдена");
            }

            CartItem itemToRemove = cart.get().getItems().stream()
                    .filter(item -> item.getId().equals(itemId))
                    .findFirst()
                    .orElseThrow(() -> {
                        logger.error("Cart item {} not found in cart", itemId);
                        return new RuntimeException("Элемент корзины не найден");
                    });

            cart.get().getItems().remove(itemToRemove);
            cartRepository.save(cart.get());
            logger.info("Successfully removed item {} from cart", itemId);
        } catch (Exception e) {
            logger.error("Error removing item {} from cart", itemId, e);
            throw e;
        }
    }
}
