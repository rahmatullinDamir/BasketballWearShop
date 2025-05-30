package org.example.basketballshop.Repositories;

import org.example.basketballshop.Models.Badge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BadgesRepository extends JpaRepository<Badge, Long> {
    Optional<Badge> findByNameIgnoreCase(String badgeName);
}
