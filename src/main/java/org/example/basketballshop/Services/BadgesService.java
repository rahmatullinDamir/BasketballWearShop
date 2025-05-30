package org.example.basketballshop.Services;

import org.example.basketballshop.DTO.Forms.BadgeForm;
import org.example.basketballshop.Models.User;

public interface BadgesService {
    boolean createBadgeWithIcon(BadgeForm badgeForm);
    void awardBadgeToUser(User user, String badgeName);

    boolean hasBadge(User user, String badgeName);
    int calculateDiscountByBadges();

}
