package org.example.basketballshop.DTO;

import lombok.Builder;
import lombok.Data;
import org.example.basketballshop.Models.Enums.GiftCertificateStatus;
import org.example.basketballshop.Models.GiftCertificate;
import org.example.basketballshop.Utils.DateTimeConverter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class GiftCertificateDto {
    private Long id;
    private BigDecimal amount;
    private String code;
    private String createdAt;
    private String expiresAt;
    private LocalDateTime usedAt;
    private String buyerEmail;
    private String usedByEmail;
    private GiftCertificateStatus status;

    public static GiftCertificateDto from(GiftCertificate certificate) {
        if (certificate == null) {
            return null;
        }
        
        return GiftCertificateDto.builder()
                .id(certificate.getId())
                .amount(certificate.getAmount())
                .code(certificate.getCode())
                .createdAt(DateTimeConverter.convertToString(certificate.getCreatedAt()))
                .expiresAt(DateTimeConverter.convertToString(certificate.getExpiresAt()))
                .usedAt(certificate.getUsedAt())
                .buyerEmail(certificate.getBuyer().getEmail())
                .usedByEmail(certificate.getUsedBy() != null ? certificate.getUsedBy().getEmail() : null)
                .status(certificate.getStatus())
                .build();
    }
} 