package org.example.basketballshop.services.Impl;

import org.example.basketballshop.dto.GiftCertificateDto;
import org.example.basketballshop.dto.forms.GiftCertificateForm;
import org.example.basketballshop.models.enums.GiftCertificateStatus;
import org.example.basketballshop.models.GiftCertificate;
import org.example.basketballshop.models.User;
import org.example.basketballshop.repositories.GiftCertificateRepository;
import org.example.basketballshop.repositories.UserRepository;
import org.example.basketballshop.services.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class GiftCertificateServiceImpl implements GiftCertificateService {

    @Autowired
    private GiftCertificateRepository giftCertificateRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public GiftCertificateDto purchaseCertificate(GiftCertificateForm form, String buyerEmail) {
        User buyer = userRepository.findByEmail(buyerEmail)
                .orElseThrow(() -> new RuntimeException("Покупатель не найден"));

        GiftCertificate certificate = GiftCertificate.builder()
                .amount(form.getAmount())
                .code(generateUniqueCode())
                .buyer(buyer)
                .build();

        return GiftCertificateDto.from(giftCertificateRepository.save(certificate));
    }

    @Override
    public List<GiftCertificateDto> getPurchasedCertificates(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        return giftCertificateRepository.findByBuyerIdOrderByCreatedAtDesc(user.getId())
                .stream()
                .map(GiftCertificateDto::from)
                .collect(Collectors.toList());
    }

    @Override
    public List<GiftCertificateDto> getUsedCertificates(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        return giftCertificateRepository.findByUsedByIdOrderByUsedAtDesc(user.getId())
                .stream()
                .map(GiftCertificateDto::from)
                .collect(Collectors.toList());
    }

    @Override
    public GiftCertificateDto validateCertificate(String code) {
        GiftCertificate certificate = giftCertificateRepository.findByCodeAndStatus(code, GiftCertificateStatus.ACTIVE)
                .orElseThrow(() -> new RuntimeException("Сертификат не найден или уже использован"));

        if (certificate.getExpiresAt().isBefore(LocalDateTime.now())) {
            certificate.setStatus(GiftCertificateStatus.EXPIRED);
            giftCertificateRepository.save(certificate);
            throw new RuntimeException("Срок действия сертификата истек");
        }

        return GiftCertificateDto.from(certificate);
    }

    @Override
    public void applyCertificateToOrder(String code, String userEmail, Long orderId) {
        GiftCertificate certificate = giftCertificateRepository.findByCodeAndStatus(code, GiftCertificateStatus.ACTIVE)
                .orElseThrow(() -> new RuntimeException("Сертификат не найден или уже использован"));

        if (certificate.getExpiresAt().isBefore(LocalDateTime.now())) {
            certificate.setStatus(GiftCertificateStatus.EXPIRED);
            giftCertificateRepository.save(certificate);
            throw new RuntimeException("Срок действия сертификата истек");
        }

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        certificate.setUsedBy(user);
        certificate.setStatus(GiftCertificateStatus.USED);
        certificate.setUsedAt(LocalDateTime.now());
        giftCertificateRepository.save(certificate);
    }

    private String generateUniqueCode() {
        return "GC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
} 