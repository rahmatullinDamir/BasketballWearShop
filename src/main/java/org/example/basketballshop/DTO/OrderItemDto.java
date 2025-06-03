package org.example.basketballshop.DTO;

import lombok.Builder;
import lombok.Data;
import org.example.basketballshop.Models.OrderItem;
import org.example.basketballshop.Models.ProductSize;

import java.math.BigDecimal;

@Data
@Builder
public class OrderItemDto {
    private Long id;
    private String productName;
    private int quantity;
    private String size;
    private BigDecimal price;
    private BigDecimal totalPrice;

    public static OrderItemDto from(OrderItem item) {
        String sizeName = item.getProduct().getSizes().stream()
                .filter(ps -> ps.getSize().equals(item.getSize()))
                .map(ProductSize::getSize)
                .findFirst()
                .orElse(item.getSize());

        return OrderItemDto.builder()
                .id(item.getId())
                .productName(item.getProduct().getName())
                .quantity(item.getQuantity())
                .size(sizeName)
                .price(item.getPrice())
                .totalPrice(item.getTotalPrice())
                .build();
    }
} 