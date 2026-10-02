package org.example.basketballshop.dto;

import lombok.Builder;
import lombok.Data;
import org.example.basketballshop.models.Order;
import org.example.basketballshop.utils.DateTimeConverter;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class OrderDto {
    private Long id;
    private String createdAt;
    private BigDecimal total;
    private int discount;
    private List<OrderItemDto> items;

    public static OrderDto from(Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .createdAt(DateTimeConverter.convertToString(order.getCreatedAt()))
                .total(order.getTotal())
                .discount(order.getDiscount())
                .items(order.getItems().stream()
                        .map(OrderItemDto::from)
                        .collect(Collectors.toList()))
                .build();
    }
} 