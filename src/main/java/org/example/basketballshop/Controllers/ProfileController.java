package org.example.basketballshop.Controllers;


import org.example.basketballshop.DTO.AddressDto;
import org.example.basketballshop.DTO.UserDto;
import org.example.basketballshop.Models.User;
import org.example.basketballshop.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
public class ProfileController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public String getProfilePage(Model model) {

        Optional<AddressDto> userAddress = userService.isUserHaveAddress();
        userAddress.ifPresent(addressDto -> model.addAttribute("userAddress", addressDto));
        UserDto user = UserDto.in(userService.getUserFromSession());
        model.addAttribute("user", user);
        return "profile_page";
    }


}
