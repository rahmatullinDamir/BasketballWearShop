package org.example.basketballshop.controllers;

import org.example.basketballshop.dto.ProductDto;
import org.example.basketballshop.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdminPageController {

    @Autowired
    private ProductService productService;

    @GetMapping("/admin")
    public String getAdminPage(Model model) {
        List<ProductDto> getAllProducts = productService.getAllProductsWithDiscount(0);
        model.addAttribute("products", getAllProducts);
        return "admin_page";
    }
}
