package org.example.basketballshop.DTO;


import lombok.Builder;
import lombok.Data;
import org.example.basketballshop.Models.Badge;
import org.example.basketballshop.Models.ImageInfo;

import java.util.List;

@Data
@Builder
public class BadgeDto {
    private String name;
    private String description;
    private int requiredPoints;
    private ImageInfo iconImageInfo;

    public static BadgeDto in(Badge badge) {
        return BadgeDto.builder()
                .name(badge.getName())
                .description(badge.getDescription())
                .requiredPoints(badge.getRequiredPoints())
                .iconImageInfo(badge.getIconImageInfo())
                .build();
    }

    public static List<BadgeDto> from(List<Badge> badges) {
        return badges.stream().map(BadgeDto::in).toList();
    }
}
