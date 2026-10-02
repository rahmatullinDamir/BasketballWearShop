package org.example.basketballshop.repositories;

import org.example.basketballshop.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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