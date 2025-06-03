package org.example.basketballshop.Controllers;

import org.example.basketballshop.DTO.AddressDto;
import org.example.basketballshop.DTO.OrderDto;
import org.example.basketballshop.DTO.UserDto;
import org.example.basketballshop.Models.User;
import org.example.basketballshop.Services.OrderService;
import org.example.basketballshop.Services.UserService;
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
        
        UserDto userDto = UserDto.in(user);
        List<OrderDto> orders = orderService.getUserOrders(user.getEmail());
        
        model.addAttribute("user", userDto);
        model.addAttribute("orders", orders);
        return "profile_page";
    }
}
