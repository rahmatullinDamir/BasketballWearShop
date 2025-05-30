package org.example.basketballshop.DTO;


import lombok.Builder;
import lombok.Data;
import org.example.basketballshop.Models.Address;
import org.example.basketballshop.Models.Badge;
import org.example.basketballshop.Models.Enums.UserRole;
import org.example.basketballshop.Models.User;

import javax.management.relation.Role;
import java.util.List;

@Data
@Builder
public class UserDto {
    private String username;
    private String email;
    private List<BadgeDto> badges;
    private String role;

    public static UserDto in(User user) {
        return UserDto.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .badges(BadgeDto.from(user.getBadges()))
                .role(user.getRole().name())
                .build();
    }

    public static List<UserDto> from(List<User> users) {
        return users.stream().map(UserDto::in).toList();
    }
}
