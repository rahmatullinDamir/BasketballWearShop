package org.example.basketballshop.mapper;

import lombok.RequiredArgsConstructor;
import org.example.basketballshop.dto.BadgeDto;
import org.example.basketballshop.models.Badge;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public final class BadgeDtoMapper {
    private final ImageInfoMapper imageInfoMapper;

    public BadgeDto in(Badge badge) {
        if (badge == null) {
            return null;
        }

        return BadgeDto.builder()
                .name(badge.getName())
                .description(badge.getDescription())
                .requiredPoints(badge.getRequiredPoints())
                .iconImageInfo(imageInfoMapper.in(badge.getIconImageInfo()))
                .build();
    }

    public List<BadgeDto> from(List<Badge> badges) {
        return badges.stream()
                .map(this::in)
                .toList();
    }
}
