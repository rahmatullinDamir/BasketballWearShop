package org.example.basketballshop.services;

import org.example.basketballshop.dto.AddressDto;
import org.example.basketballshop.dto.UserDto;
import org.example.basketballshop.models.User;

import java.util.Optional;

public interface UserService {
    Optional<AddressDto> isUserHaveAddress();
    User getUserFromSession();
    UserDto getUserDtoFromSession(User user);
}
