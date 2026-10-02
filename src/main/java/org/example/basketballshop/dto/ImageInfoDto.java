package org.example.basketballshop.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImageInfoDto {
    private Long id;
    private String originalName;
    private String imageUrl;
}
