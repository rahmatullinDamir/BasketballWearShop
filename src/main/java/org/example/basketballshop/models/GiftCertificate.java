package org.example.basketballshop.models;

import jakarta.persistence.*;
import lombok.*;
import org.example.basketballshop.models.enums.GiftCertificateStatus;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "gift_certificate")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GiftCertificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false, name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "used_at")
    private LocalDateTime usedAt;

    @Column(nullable = false, name = "expires_at")
    private LocalDateTime expiresAt;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private User buyer;

    @ManyToOne
    @JoinColumn(name = "used_by_id")
    private User usedBy;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GiftCertificateStatus status;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        expiresAt = createdAt.plusMonths(12); // Сертификат действителен 12 месяцев
        status = GiftCertificateStatus.ACTIVE;
    }
}