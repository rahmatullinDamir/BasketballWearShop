package org.example.basketballshop.dto;


import lombok.Builder;
import lombok.Data;
import org.example.basketballshop.models.User;

import java.util.List;

@Data
@Builder
public class UserDto {
    private String username;
    private String email;
    private List<BadgeDto> badges;
    private String role;
}
