package org.example.basketballshop.Models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items = new ArrayList<>();

//    public void addItem(CartItem item) {
//        items.add(item);
//        item.setCart(this);
//    }
//
//    public void removeItem(CartItem item) {
//        items.remove(item);
//        item.setCart(null);
//    }

    // Getters and setters
}