package org.example.basketballshop.Services.Impl;

import org.example.basketballshop.DTO.AddressDto;
import org.example.basketballshop.Models.User;
import org.example.basketballshop.Repositories.UserRepository;
import org.example.basketballshop.Services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUserFromSession() {
        try {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            logger.info("Attempting to get user from session with email: {}", email);

            return userRepository.findByEmail(email)
                    .orElseThrow(() -> {
                        logger.error("User not found for email: {}", email);
                        return new UsernameNotFoundException("User not found with email: " + email);
                    });
        } catch (Exception e) {
            logger.error("Error getting user from session", e);
            throw e;
        }
    }

    @Override
    public Optional<AddressDto> isUserHaveAddress() {
        try {
            User user = getUserFromSession();
            logger.info("Checking address for user: {}", user.getEmail());

            if (user.getAddress() == null) {
                logger.info("No address found for user: {}", user.getEmail());
                return Optional.empty();
            }

            logger.info("Address found for user: {}", user.getEmail());
            return Optional.of(AddressDto.in(user.getAddress()));
        } catch (Exception e) {
            logger.error("Error checking user address", e);
            throw e;
        }
    }
}
