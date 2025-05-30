package org.example.basketballshop.Models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class GiftCertificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code; // Уникальный код сертификата (например: GC-ABCD1234)

    private BigDecimal value; // Номинал сертификата (например: 5000.00 руб.)

    private LocalDateTime expirationDate;

    private boolean used = false;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "recipient_id")
    private User recipient;

    // Getters and setters
}