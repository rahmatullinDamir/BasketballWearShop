package org.example.basketballshop.DTO;

import lombok.Builder;
import lombok.Data;
import org.example.basketballshop.Models.GiftCertificate;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class CartDto {
    private List<CartItemDto> items;
    private int discount;
    private BigDecimal total;
    private GiftCertificateDto appliedCertificate;
}