package org.example.basketballshop.Repositories;

import org.example.basketballshop.Models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    @Query("SELECT new map(" +
           "p.id as productId, " +
           "p.name as productName, " +
           "oi.size as size, " +
           "COUNT(oi) as orderCount, " +
           "SUM(oi.quantity) as totalQuantity) " +
           "FROM Product p " +
           "JOIN OrderItem oi ON oi.product = p " +
           "GROUP BY p.id, p.name, oi.size " +
           "HAVING COUNT(oi) > 0 " +
           "ORDER BY p.name, SUM(oi.quantity) DESC")
    List<Map<String, Object>> findPopularSizesByProduct();
}
