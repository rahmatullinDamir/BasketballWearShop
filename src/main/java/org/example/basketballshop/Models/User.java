package org.example.basketballshop.Models;

import jakarta.persistence.*;
import lombok.*;
import org.example.basketballshop.Models.Enums.UserRole;
import org.hibernate.annotations.Fetch;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Setter
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Address address;

    // Связи
    @OneToMany(mappedBy = "user")
    private List<Order> orders = new ArrayList<>();

    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
    private List<Badge> badges;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Cart cart;

    @OneToMany(mappedBy = "recipient")
    private List<GiftCertificate> receivedCertificates = new ArrayList<>();

    @OneToMany(mappedBy = "sender")
    private List<GiftCertificate> sentCertificates = new ArrayList<>();

    public User() {
        this.badges = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public UserRole getRole() {
        return role;
    }

    public Address getAddress() {
        return address;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public List<Badge> getBadges() {
        if (badges == null) {
            badges = new ArrayList<>();
        }
        return badges;
    }

    public Cart getCart() {
        return cart;
    }

    public List<GiftCertificate> getReceivedCertificates() {
        return receivedCertificates;
    }

    public List<GiftCertificate> getSentCertificates() {
        return sentCertificates;
    }
}