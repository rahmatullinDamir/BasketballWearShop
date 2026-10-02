package org.example.basketballshop.controllers;

import org.example.basketballshop.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Map;

@Controller
@RequestMapping("/statistics")
public class OrderStatisticsController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public String getStatistics(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        Map<String, Object> statistics = orderService.getUserOrderStatistics(principal.getName());
        model.addAttribute("statistics", statistics);

        return "order_statistics";
    }

} 