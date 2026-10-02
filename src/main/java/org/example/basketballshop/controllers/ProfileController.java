package org.example.basketballshop.controllers;

import org.example.basketballshop.dto.AddressDto;
import org.example.basketballshop.dto.OrderDto;
import org.example.basketballshop.dto.UserDto;
import org.example.basketballshop.models.User;
import org.example.basketballshop.services.OrderService;
import org.example.basketballshop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class ProfileController {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/profile")
    public String getProfilePage(Model model) {
        User user = userService.getUserFromSession();
        Optional<AddressDto> userAddress = userService.isUserHaveAddress();
        userAddress.ifPresent(addressDto -> model.addAttribute("userAddress", addressDto));

        UserDto userDto = userService.getUserDtoFromSession(user);
        List<OrderDto> orders = orderService.getUserOrders(user.getEmail());
        
        model.addAttribute("user", userDto);
        model.addAttribute("orders", orders);
        return "profile_page";
    }
}
