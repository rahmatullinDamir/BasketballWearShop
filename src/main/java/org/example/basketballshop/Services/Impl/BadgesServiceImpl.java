package org.example.basketballshop.Services.Impl;

import org.example.basketballshop.DTO.Forms.BadgeForm;
import org.example.basketballshop.Models.Badge;
import org.example.basketballshop.Models.ImageInfo;
import org.example.basketballshop.Models.User;
import org.example.basketballshop.Repositories.BadgesRepository;
import org.example.basketballshop.Repositories.UserRepository;
import org.example.basketballshop.Services.BadgesService;
import org.example.basketballshop.Services.ImageInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BadgesServiceImpl implements BadgesService {

    @Autowired
    private BadgesRepository badgeRepository;

    @Autowired
    private ImageInfoService imageInfoService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserRepository userRepository;


    // Проверить, есть ли у пользователя определённый значок
    public boolean hasBadge(User user, String badgeName) {
        return user.getBadges().stream()
                .anyMatch(badge -> badge.getName().equalsIgnoreCase(badgeName));
    }

    // Начислить значок пользователю, если он ещё не имеет его
    public void awardBadgeToUser(User user, String badgeName) {
        Badge badge = badgeRepository.findByNameIgnoreCase(badgeName)
                .orElseThrow(() -> new RuntimeException("Значок не найден"));

        if (!hasBadge(user, badgeName)) {
            user.getBadges().add(badge);
            badge.getUsers().add(user);
            userRepository.save(user);
        }
    }

    // Пример: Разблокировка скидки по количеству значков
    public int calculateDiscountByBadges() {
        User user = userService.getUserFromSession();
        int badgeCount = user.getBadges().size();

        if (badgeCount >= 5) return 15; // 15% скидка за 5 значков
        if (badgeCount >= 3) return 10; // 10% скидка за 3 значка
        if (badgeCount >= 1) return 5;  // 5% скидка за 1 значок

        return 0; // нет скидки
    }

    @Override
    public boolean createBadgeWithIcon(BadgeForm badgeForm) {
        ImageInfo iconImage = imageInfoService.saveImage(badgeForm.getIcon()); // твой уже существующий метод

        Badge badge = Badge.builder()
                .name(badgeForm.getName())
                .description(badgeForm.getDescription())
                .requiredPoints(badgeForm.getRequiredPoints())
                .iconImageInfo(iconImage)
                .build();

        badgeRepository.save(badge);

        return true;
    }
}
