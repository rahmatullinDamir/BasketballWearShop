package org.example.basketballshop.services;

import org.example.basketballshop.dto.OrderDto;
import org.example.basketballshop.models.Order;

import java.util.List;
import java.util.Map;

public interface OrderService {
    Order createOrderFromCart(String userEmail);

    List<OrderDto> getUserOrders(String userEmail);

    Map<String, Object> getUserOrderStatistics(String userEmail);
}