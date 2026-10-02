package org.example.basketballshop.services.Impl;

import lombok.RequiredArgsConstructor;
import org.example.basketballshop.dto.AddressDto;
import org.example.basketballshop.dto.UserDto;
import org.example.basketballshop.mapper.BadgeDtoMapper;
import org.example.basketballshop.mapper.ImageInfoMapper;
import org.example.basketballshop.models.User;
import org.example.basketballshop.repositories.UserRepository;
import org.example.basketballshop.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final BadgeDtoMapper badgeDtoMapper;

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
    public UserDto getUserDtoFromSession(User user) {
        return mapToUserDto(user);
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

    private UserDto mapToUserDto(User user) {
        if (user == null) {
            return null;
        }
            return UserDto.builder()
                    .email(user.getEmail())
                    .username(user.getUsername())
                    .badges(badgeDtoMapper.from(user.getBadges()))
                    .role(user.getRole().name())
                    .build();
    }
}
