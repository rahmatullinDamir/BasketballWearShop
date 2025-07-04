package org.example.basketballshop.Repositories;

import org.example.basketballshop.Models.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    Address findByUserId(Long userId);
}
