package org.example.basketballshop.Services;

import org.example.basketballshop.DTO.AddressDto;
import org.example.basketballshop.DTO.Forms.AddressForm;

import java.util.Optional;

public interface AddressService {
    AddressDto addAddress(AddressForm addressForm);
    boolean updateAddress(Long addressId, AddressForm addressForm);

    Optional<AddressDto> getAddress(Long addressId);
    boolean deleteAddress(Long addressId);

}
