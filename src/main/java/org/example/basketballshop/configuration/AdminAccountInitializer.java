package org.example.basketballshop.configuration;

import lombok.RequiredArgsConstructor;
import org.example.basketballshop.models.User;
import org.example.basketballshop.models.enums.UserRole;
import org.example.basketballshop.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminAccountInitializer {

    private static final Logger logger = LoggerFactory.getLogger(AdminAccountInitializer.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.default-admin.email}")
    private String adminEmail;

    @Value("${app.default-admin.password}")
    private String adminPassword;

    @Value("${app.default-admin.username}")
    private String adminUsername;

    @EventListener(ApplicationReadyEvent.class)
    public void initializeAdminAccount() {
        if (userRepository.findByEmail(adminEmail).isEmpty()) {
            try {
                logger.info("Admin account not found. Creating default admin with email: {}", adminEmail);

                User admin = new User();
                admin.setEmail(adminEmail);
                admin.setUsername(adminUsername);

                admin.setPassword(passwordEncoder.encode(adminPassword));

                admin.setRole(UserRole.ADMIN);

                userRepository.save(admin);
                logger.info("Default admin account created successfully.");

            } catch (DataIntegrityViolationException e) {
                logger.info("Admin account was already created by another instance in the cluster.");
            } catch (Exception e) {
                logger.error("Unexpected error during admin account initialization", e);
            }
        } else {
            logger.info("Admin account with email {} already exists. Initialization skipped.", adminEmail);
        }
    }
}
