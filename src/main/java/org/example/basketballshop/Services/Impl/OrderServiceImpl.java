package org.example.basketballshop.Services.Impl;

import org.example.basketballshop.DTO.OrderDto;
import org.example.basketballshop.Models.*;
import org.example.basketballshop.Models.Enums.GiftCertificateStatus;
import org.example.basketballshop.Repositories.CartRepository;
import org.example.basketballshop.Repositories.GiftCertificateRepository;
import org.example.basketballshop.Repositories.OrderRepository;
import org.example.basketballshop.Repositories.UserRepository;
import org.example.basketballshop.Services.BadgesService;
import org.example.basketballshop.Services.CartService;
import org.example.basketballshop.Services.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private GiftCertificateRepository giftCertificateRepository;

    @Autowired
    private BadgesService badgesService;

    @Override
    @Transactional
    public Order createOrderFromCart(String userEmail) {
        try {
            logger.info("Creating order from cart for user: {}", userEmail);
            
            User user = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> {
                        logger.error("User not found with email: {}", userEmail);
                        return new RuntimeException("User not found");
                    });

            Cart cart = cartRepository.findByUserId(user.getId())
                    .orElseThrow(() -> {
                        logger.error("Cart not found for user: {}", userEmail);
                        return new RuntimeException("Cart not found");
                    });

            if (cart.getItems().isEmpty()) {
                logger.error("Attempted to create order with empty cart for user: {}", userEmail);
                throw new RuntimeException("Cart is empty");
            }

            Order order = new Order();
            order.setUser(user);

            logger.info("Calculating subtotal for order");
            BigDecimal subtotal = cart.getItems().stream()
                    .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            if (cart.getAppliedCertificate() != null) {
                logger.info("Processing gift certificate for order");
                GiftCertificate certificate = cart.getAppliedCertificate();
                
                if (certificate.getStatus() != GiftCertificateStatus.ACTIVE || 
                    certificate.getExpiresAt().isBefore(LocalDateTime.now())) {
                    logger.error("Invalid certificate used in order for user: {}", userEmail);
                    throw new RuntimeException("Сертификат недействителен");
                }

                logger.info("Calculating total with certificate");
                BigDecimal total = cartService.calculateTotalWithCertificate(cart, subtotal);
                order.setTotal(total);

                logger.info("Marking certificate as used");
                certificate.setStatus(GiftCertificateStatus.USED);
                certificate.setUsedBy(user);
                certificate.setUsedAt(LocalDateTime.now());
                giftCertificateRepository.save(certificate);
                
                cart.setAppliedCertificate(null);
            } else {
                order.setTotal(subtotal);
            }

            logger.info("Converting cart items to order items");
            cart.getItems().forEach(cartItem -> {
                try {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setProduct(cartItem.getProduct());
                    orderItem.setQuantity(cartItem.getQuantity());
                    
                    String sizeName = cartItem.getProduct().getSizes().stream()
                            .filter(ps -> ps.getId().equals(cartItem.getProductSizeId()))
                            .map(ProductSize::getSize)
                            .findFirst()
                            .orElse("Unknown Size");
                    
                    orderItem.setSize(sizeName);
                    orderItem.setPrice(cartItem.getProduct().getPrice());
                    order.addItem(orderItem);
                } catch (Exception e) {
                    logger.error("Error processing cart item for order", e);
                    throw e;
                }
            });

            logger.info("Clearing cart");
            cart.getItems().clear();
            cartRepository.save(cart);

            logger.info("Awarding 'Боллер' badge to user");
            badgesService.awardBadgeToUser(user, "Боллер");

            Order savedOrder = orderRepository.save(order);
            logger.info("Successfully created order {} for user {}", savedOrder.getId(), userEmail);
            return savedOrder;
        } catch (DataAccessException e) {
            logger.error("Database error while creating order for user: {}", userEmail, e);
            throw new RuntimeException("Error creating order: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Error creating order for user: {}", userEmail, e);
            throw e;
        }
    }

    @Override
    public List<OrderDto> getUserOrders(String userEmail) {
        try {
            logger.info("Retrieving orders for user: {}", userEmail);
            
            User user = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> {
                        logger.error("User not found with email: {}", userEmail);
                        return new RuntimeException("User not found");
                    });

            List<OrderDto> orders = orderRepository.findByUserIdOrderByCreatedAtDesc(user.getId())
                    .stream()
                    .map(OrderDto::from)
                    .collect(Collectors.toList());
            
            logger.info("Retrieved {} orders for user {}", orders.size(), userEmail);
            return orders;
        } catch (Exception e) {
            logger.error("Error retrieving orders for user: {}", userEmail, e);
            throw e;
        }
    }

    @Override
    public Map<String, Object> getUserOrderStatistics(String userEmail) {
        try {
            logger.info("Retrieving order statistics for user: {}", userEmail);
            
            User user = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> {
                        logger.error("User not found with email: {}", userEmail);
                        return new RuntimeException("User not found");
                    });
            
            Map<String, Object> statistics = orderRepository.getUserOrderStatistics(user.getId());
            logger.info("Successfully retrieved order statistics for user: {}", userEmail);
            return statistics;
        } catch (Exception e) {
            logger.error("Error retrieving order statistics for user: {}", userEmail, e);
            throw e;
        }
    }
} 