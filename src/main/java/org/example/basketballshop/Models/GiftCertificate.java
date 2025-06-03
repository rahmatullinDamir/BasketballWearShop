package org.example.basketballshop.Models;

import jakarta.persistence.*;
import lombok.*;
import org.example.basketballshop.Models.Enums.GiftCertificateStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
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

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime usedAt;

    @Column(nullable = false)
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