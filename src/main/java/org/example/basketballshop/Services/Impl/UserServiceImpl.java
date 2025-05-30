package org.example.basketballshop.Services.Impl;

import org.example.basketballshop.DTO.AddressDto;
import org.example.basketballshop.DTO.UserDto;
import org.example.basketballshop.Models.User;
import org.example.basketballshop.Repositories.UserRepository;
import org.example.basketballshop.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUserFromSession() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
        return user;
    }
    @Override
    public Optional<AddressDto> isUserHaveAddress() {
        User user = getUserFromSession();
        if (user.getAddress() == null) {
            return Optional.empty();
        }

        return Optional.of(AddressDto.in(user.getAddress()));
    }
}
