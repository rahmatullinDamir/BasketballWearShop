package org.example.basketballshop.Repositories;

import org.example.basketballshop.Models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
