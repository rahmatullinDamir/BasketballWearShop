package org.example.basketballshop.Services;


import org.example.basketballshop.DTO.CartDto;
import org.example.basketballshop.Models.Cart;

import java.math.BigDecimal;

public interface CartService {
    void addToCart(Long userId, Long productId, int quantity, String size);
    int getQuantityOfCart(Long userId);
    CartDto getCartWithDiscount(String userEmail);
    Cart updateQuantity(Long itemId, int quantity);

    void removeItem(Long id);

    void applyGiftCertificate(String code, String userEmail);
    void removeGiftCertificate(String userEmail);
    BigDecimal calculateTotalWithCertificate(Cart cart, BigDecimal subtotal);
}
