package org.example.basketballshop.Repositories;

import org.example.basketballshop.Models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserIdOrderByCreatedAtDesc(Long userId);

    @Query("SELECT new map(" +
           "COUNT(o) as orderCount, " +
           "SUM(o.total) as totalSpent, " +
           "AVG(o.total) as averageOrderValue, " +
           "MAX(o.total) as maxOrderValue) " +
           "FROM Order o " +
           "WHERE o.user.id = :userId")
    Map<String, Object> getUserOrderStatistics(@Param("userId") Long userId);
} 