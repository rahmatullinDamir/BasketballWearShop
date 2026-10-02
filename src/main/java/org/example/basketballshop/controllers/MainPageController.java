package org.example.basketballshop.controllers;

import org.example.basketballshop.dto.AddressDto;
import org.example.basketballshop.dto.ProductDto;
import org.example.basketballshop.services.BadgesService;
import org.example.basketballshop.services.ProductService;
import org.example.basketballshop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class MainPageController {
    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private BadgesService badgesService;

    @GetMapping("/")
    public String getMainPage(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated = authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String);
        int discount = 0;
        if (isAuthenticated) {
            Optional<AddressDto> userAddress = userService.isUserHaveAddress();
            model.addAttribute("userAddress", userAddress);
            discount = badgesService.calculateDiscountByBadges();
        }

        List<ProductDto> products = productService.getAllProductsWithDiscount(discount);
        model.addAttribute("products", products);
        model.addAttribute("isAuth", isAuthenticated);
        return "main_page";
    }
}
