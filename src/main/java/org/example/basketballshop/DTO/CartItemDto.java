package org.example.basketballshop.DTO;

import lombok.Builder;
import lombok.Data;
import org.example.basketballshop.Models.Cart;
import org.example.basketballshop.Models.CartItem;
import org.example.basketballshop.Models.ProductSize;
import org.example.basketballshop.Models.User;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class CartItemDto {
    private Long id;
    private String productName;
    private BigDecimal price;
    private int quantity;
    private String productSize;
    private Long productSizeId;
    private BigDecimal totalPrice;

    public static CartItemDto in(CartItem cartItem) {
        String sizeName = cartItem.getProduct().getSizes().stream()
                .filter(ps -> ps.getId().equals(cartItem.getProductSizeId()))
                .map(ProductSize::getSize)
                .findFirst()
                .orElse("Размер не найден");

        return CartItemDto.builder()
                .id(cartItem.getId())
                .productSizeId(cartItem.getProductSizeId())
                .productSize(sizeName)
                .productName(cartItem.getProduct().getName())
                .price(cartItem.getProduct().getPrice())
                .quantity(cartItem.getQuantity())
                .totalPrice(cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .build();
    }

    public static List<CartItemDto> from(List<CartItem> cartItems) {
        return cartItems.stream().map(CartItemDto::in).toList();
    }
}