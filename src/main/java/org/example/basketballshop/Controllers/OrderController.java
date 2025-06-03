package org.example.basketballshop.Controllers;

import org.example.basketballshop.Services.AddressService;
import org.example.basketballshop.Services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private AddressService addressService;

    @PostMapping("/create")
    public String createOrder(Principal principal, RedirectAttributes redirectAttributes) {
        if (principal == null) {
            return "redirect:/login";
        }

        if (!addressService.hasAddress(principal.getName())) {
            redirectAttributes.addAttribute("error", "Для оформления заказа необходимо указать адрес доставки");
            return "redirect:/cart";
        }

        try {
            orderService.createOrderFromCart(principal.getName());
            return "redirect:/profile";
        } catch (Exception e) {
            redirectAttributes.addAttribute("error", "Ошибка при создании заказа: " + e.getMessage());
            return "redirect:/cart";
        }
    }
} 