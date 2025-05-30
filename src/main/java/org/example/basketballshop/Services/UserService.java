package org.example.basketballshop.Services;

import org.example.basketballshop.DTO.AddressDto;
import org.example.basketballshop.DTO.UserDto;
import org.example.basketballshop.Models.User;

import java.util.Optional;

public interface UserService {
    Optional<AddressDto> isUserHaveAddress();
    User getUserFromSession();
}
