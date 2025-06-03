package org.example.basketballshop.Controllers;

import org.example.basketballshop.DTO.AddressDto;
import org.example.basketballshop.DTO.ProductDto;
import org.example.basketballshop.Services.BadgesService;
import org.example.basketballshop.Services.ProductService;
import org.example.basketballshop.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
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
        System.out.println("PRODUCTS " + products);
        model.addAttribute("products", products);
        model.addAttribute("isAuth", isAuthenticated);
        return "main_page";
    }
}
