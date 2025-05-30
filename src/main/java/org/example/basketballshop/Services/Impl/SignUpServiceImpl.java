package org.example.basketballshop.Services.Impl;

import org.example.basketballshop.DTO.Forms.UserForm;
import org.example.basketballshop.Models.Enums.UserRole;
import org.example.basketballshop.Models.User;
import org.example.basketballshop.Repositories.UserRepository;
import org.example.basketballshop.Services.BadgesService;
import org.example.basketballshop.Services.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SignUpServiceImpl implements SignUpService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BadgesService badgeService;

    @Autowired
    private PasswordEncoder encoder;
    @Override
    public void signUp(UserForm userForm) {
        User user = User.builder()
                .email(userForm.getEmail())
                .password(encoder.encode(userForm.getPassword()))
                .username(userForm.getUsername())
                .role(UserRole.USER)
                .build();
        try {
            userRepository.save(user);
            badgeService.awardBadgeToUser(user, "Новичок");
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
    }
}
