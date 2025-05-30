package org.example.basketballshop.DTO.Forms;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class BadgeForm {
    private String name;
    private String description;
    private int requiredPoints;
    private MultipartFile icon;
}
