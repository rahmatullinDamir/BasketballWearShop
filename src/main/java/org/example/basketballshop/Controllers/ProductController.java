package org.example.basketballshop.Controllers;

import jakarta.validation.Valid;
import org.example.basketballshop.DTO.Forms.ProductForm;
import org.example.basketballshop.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/products/save")
    public String saveProduct(
            @Valid ProductForm productForm,
            RedirectAttributes redirectAttributes
    ) {
        if (productService.saveProduct(productForm)) {

            redirectAttributes.addFlashAttribute("message", "Товар успешно добавлен!");
        }
        else {
            redirectAttributes.addFlashAttribute("message", "Ошибка при добавлении товара");
        }
        return "redirect:/admin";
    }
}
