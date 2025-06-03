package org.example.basketballshop.Services;

import org.example.basketballshop.DTO.OrderDto;
import org.example.basketballshop.Models.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface OrderService {
    Order createOrderFromCart(String userEmail);

    List<OrderDto> getUserOrders(String userEmail);

    Map<String, Object> getUserOrderStatistics(String userEmail);
}