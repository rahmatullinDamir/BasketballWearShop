package org.example.basketballshop.services.Impl;

import org.example.basketballshop.dto.forms.UserForm;
import org.example.basketballshop.models.enums.SystemBadge;
import org.example.basketballshop.models.enums.UserRole;
import org.example.basketballshop.models.User;
import org.example.basketballshop.repositories.UserRepository;
import org.example.basketballshop.services.BadgesService;
import org.example.basketballshop.services.SignUpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.dao.DataIntegrityViolationException;

@Service
public class SignUpServiceImpl implements SignUpService {
    private static final Logger logger = LoggerFactory.getLogger(SignUpServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BadgesService badgeService;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public void signUp(UserForm userForm) {
        try {
            logger.info("Starting user registration process for email: {}", userForm.getEmail());

            // Check if user already exists
            if (userRepository.findByEmail(userForm.getEmail()).isPresent()) {
                logger.error("User with email {} already exists", userForm.getEmail());
                throw new IllegalStateException("User with this email already exists");
            }

            User user = User.builder()
                    .email(userForm.getEmail())
                    .password(encoder.encode(userForm.getPassword()))
                    .username(userForm.getUsername())
                    .role(UserRole.USER)
                    .build();

            logger.info("Saving new user with username: {}", user.getUsername());
            userRepository.save(user);

            logger.info("Awarding 'Новичок' badge to user: {}", user.getUsername());
            badgeService.awardBadgeToUser(user, SystemBadge.NOVICE.getTitle());

            logger.info("Successfully completed registration for user: {}", user.getUsername());
        } catch (DataIntegrityViolationException e) {
            logger.error("Database error during user registration", e);
            throw new IllegalStateException("Error during user registration: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error during user registration", e);
            throw new IllegalStateException("Error during user registration: " + e.getMessage());
        }
    }
}
