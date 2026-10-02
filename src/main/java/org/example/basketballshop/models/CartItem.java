package org.example.basketballshop.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "cart_item")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;

    @ManyToOne
    private Product product;

    @Column(name = "product_size_id")
    private Long productSizeId;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

}