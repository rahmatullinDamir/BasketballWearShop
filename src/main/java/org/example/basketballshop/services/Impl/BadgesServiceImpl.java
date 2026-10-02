package org.example.basketballshop.services.Impl;

import lombok.RequiredArgsConstructor;
import org.example.basketballshop.dto.forms.BadgeForm;
import org.example.basketballshop.models.Badge;
import org.example.basketballshop.models.ImageInfo;
import org.example.basketballshop.models.User;
import org.example.basketballshop.repositories.BadgesRepository;
import org.example.basketballshop.repositories.UserRepository;
import org.example.basketballshop.services.BadgesService;
import org.example.basketballshop.services.ImageInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BadgesServiceImpl implements BadgesService {
    private static final Logger logger = LoggerFactory.getLogger(BadgesServiceImpl.class);

    private final BadgesRepository badgeRepository;
    private final ImageInfoService imageInfoService;
    private final UserServiceImpl userService;
    private final UserRepository userRepository;

    public boolean hasBadge(User user, String badgeName) {
        try {
            logger.info("Checking if user {} has badge: {}", user.getEmail(), badgeName);
            boolean hasBadge = user.getBadges().stream()
                    .anyMatch(badge -> badge.getName().equalsIgnoreCase(badgeName));
            logger.info("User {} {} badge {}", user.getEmail(), hasBadge ? "has" : "does not have", badgeName);
            return hasBadge;
        } catch (Exception e) {
            logger.error("Error checking badge {} for user {}", badgeName, user.getEmail(), e);
            throw e;
        }
    }

    @Override
    @Transactional
    public void awardBadgeToUser(User user, String badgeName) {
        try {
            logger.info("Attempting to award badge {} to user {}", badgeName, user.getEmail());

            Badge badge = badgeRepository.findByNameIgnoreCase(badgeName)
                    .orElseThrow(() -> {
                        logger.error("Critical configuration error: System badge '{}' not found in DB.", badgeName);
                        return new IllegalStateException("Значок '" + badgeName + "' не найден в базе данных. Проверьте выполнение миграций Liquibase.");
                    });

            if (!hasBadge(user, badgeName)) {
                logger.info("Adding badge {} to user {}", badgeName, user.getEmail());

                user.getBadges().add(badge);
                badge.getUsers().add(user);
                userRepository.save(user);

                logger.info("Successfully awarded badge {} to user {}", badgeName, user.getEmail());
            } else {
                logger.info("User {} already has badge {}", user.getEmail(), badgeName);
            }
        } catch (DataAccessException e) {
            logger.error("Database error while awarding badge {} to user {}", badgeName, user.getEmail(), e);
            throw new RuntimeException("Ошибка базы данных при выдаче значка: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error awarding badge {} to user {}", badgeName, user.getEmail(), e);
            throw e;
        }
    }

    public int calculateDiscountByBadges() {
        try {
            User user = userService.getUserFromSession();
            int badgeCount = user.getBadges().size();
            logger.info("Calculating discount for user {} with {} badges", user.getEmail(), badgeCount);

            int discount;
            if (badgeCount >= 5) {
                discount = 15;
            } else if (badgeCount >= 3) {
                discount = 10;
            } else if (badgeCount >= 1) {
                discount = 5;
            } else {
                discount = 0;
            }

            logger.info("Calculated {}% discount for user {} based on badges", discount, user.getEmail());
            return discount;
        } catch (Exception e) {
            logger.error("Error calculating discount by badges", e);
            throw e;
        }
    }

    @Override
    @Transactional
    public boolean createBadgeWithIcon(BadgeForm badgeForm) {
        try {
            logger.info("Creating new badge: {}", badgeForm.getName());

            logger.info("Saving badge icon");
            ImageInfo iconImage = imageInfoService.saveImage(badgeForm.getIcon());

            Badge badge = Badge.builder()
                    .name(badgeForm.getName())
                    .description(badgeForm.getDescription())
                    .requiredPoints(badgeForm.getRequiredPoints())
                    .iconImageInfo(iconImage)
                    .build();

            badgeRepository.save(badge);
            logger.info("Successfully created badge: {}", badgeForm.getName());
            return true;
        } catch (DataAccessException e) {
            logger.error("Database error while creating badge: {}", badgeForm.getName(), e);
            throw new RuntimeException("Error creating badge: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Error creating badge: {}", badgeForm.getName(), e);
            throw e;
        }
    }
}