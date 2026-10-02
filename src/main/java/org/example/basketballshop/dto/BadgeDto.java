package org.example.basketballshop.dto;


import lombok.Builder;
import lombok.Data;
import org.example.basketballshop.models.Badge;
import org.example.basketballshop.models.ImageInfo;

import java.util.List;

@Data
@Builder
public class BadgeDto {
    private String name;
    private String description;
    private int requiredPoints;
    private ImageInfoDto iconImageInfo;
}
