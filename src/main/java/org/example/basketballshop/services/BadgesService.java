package org.example.basketballshop.services;

import org.example.basketballshop.dto.forms.BadgeForm;
import org.example.basketballshop.models.User;

public interface BadgesService {
    boolean createBadgeWithIcon(BadgeForm badgeForm);
    void awardBadgeToUser(User user, String badgeName);

    boolean hasBadge(User user, String badgeName);
    int calculateDiscountByBadges();

}
