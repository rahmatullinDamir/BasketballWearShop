package org.example.basketballshop.repositories;

import org.example.basketballshop.models.GiftCertificate;
import org.example.basketballshop.models.enums.GiftCertificateStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GiftCertificateRepository extends JpaRepository<GiftCertificate, Long> {
    List<GiftCertificate> findByBuyerIdOrderByCreatedAtDesc(Long buyerId);
    List<GiftCertificate> findByUsedByIdOrderByUsedAtDesc(Long usedById);
    Optional<GiftCertificate> findByCodeAndStatus(String code, GiftCertificateStatus status);
} 