package org.example.basketballshop.Controllers;

import org.example.basketballshop.DTO.ProductDto;
import org.example.basketballshop.Models.Product;
import org.example.basketballshop.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
