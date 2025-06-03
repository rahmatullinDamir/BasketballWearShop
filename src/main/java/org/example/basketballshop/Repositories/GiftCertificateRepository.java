package org.example.basketballshop.Repositories;

import org.example.basketballshop.Models.GiftCertificate;
import org.example.basketballshop.Models.Enums.GiftCertificateStatus;
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