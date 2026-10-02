package org.example.basketballshop.services;

import org.example.basketballshop.dto.AddressDto;
import org.example.basketballshop.dto.forms.AddressForm;

import java.util.Optional;

public interface AddressService {
    AddressDto addAddress(AddressForm addressForm);
    boolean updateAddress(Long addressId, AddressForm addressForm);

    Optional<AddressDto> getAddress(Long addressId);
    boolean deleteAddress(Long addressId);
    boolean hasAddress(String email);
}
