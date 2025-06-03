package org.example.basketballshop.Controllers;

import org.example.basketballshop.DTO.OrderDto;
import org.example.basketballshop.Services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
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