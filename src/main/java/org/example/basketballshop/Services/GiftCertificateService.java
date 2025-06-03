package org.example.basketballshop.Services;

import org.example.basketballshop.DTO.GiftCertificateDto;
import org.example.basketballshop.DTO.Forms.GiftCertificateForm;

import java.util.List;

public interface GiftCertificateService {
    GiftCertificateDto purchaseCertificate(GiftCertificateForm form, String buyerEmail);
    List<GiftCertificateDto> getPurchasedCertificates(String userEmail);
    List<GiftCertificateDto> getUsedCertificates(String userEmail);
    GiftCertificateDto validateCertificate(String code);
    void applyCertificateToOrder(String code, String userEmail, Long orderId);
} 