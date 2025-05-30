package org.example.basketballshop.Services.Impl;

import jakarta.transaction.Transactional;
import org.example.basketballshop.DTO.AddressDto;
import org.example.basketballshop.DTO.Forms.AddressForm;
import org.example.basketballshop.Models.Address;
import org.example.basketballshop.Models.User;
import org.example.basketballshop.Repositories.AddressRepository;
import org.example.basketballshop.Repositories.UserRepository;
import org.example.basketballshop.Services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@Transactional
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public AddressDto addAddress(AddressForm addressForm) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));


        Address address = Address.builder()
                .city(addressForm.getCity())
                .country(addressForm.getCountry())
                .street(addressForm.getStreet())
                .postalCode(addressForm.getPostalCode())
                .user(user)
                .build();

        addressRepository.save(address);

        return AddressDto.in(address);
    }

    @Override
    public boolean updateAddress(Long addressId, AddressForm addressForm) {
        Address address = addressRepository.findById(addressId).orElse(null);
        if (address == null) {
            return false;
        }

        if (addressForm.getCity() != null) address.setCity(addressForm.getCity());
        if (addressForm.getCountry() != null) address.setCountry(addressForm.getCountry());
        if (addressForm.getStreet() != null) address.setStreet(addressForm.getStreet());
        if (addressForm.getPostalCode() != null) address.setPostalCode(addressForm.getPostalCode());

        addressRepository.save(address);
        return true;
    }

    @Override
    public Optional<AddressDto> getAddress(Long addressId) {
        Optional<Address> address = addressRepository.findById(addressId);
        return Optional.of(address.map(AddressDto::in)).orElse(Optional.empty());
    }

    @Override
    public boolean deleteAddress(Long addressId) {
        if (addressRepository.existsById(addressId)) {
            addressRepository.deleteById(addressId);
            return true;
        }
        return false;
    }
}
