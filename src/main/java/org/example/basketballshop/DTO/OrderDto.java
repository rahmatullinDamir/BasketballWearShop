package org.example.basketballshop.DTO;

import lombok.Builder;
import lombok.Data;
import org.example.basketballshop.Models.Order;
import org.example.basketballshop.Utils.DateTimeConverter;

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