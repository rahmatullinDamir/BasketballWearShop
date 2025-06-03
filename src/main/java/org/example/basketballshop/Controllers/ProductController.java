package org.example.basketballshop.Controllers;

import jakarta.validation.Valid;
import org.example.basketballshop.DTO.Forms.ProductForm;
import org.example.basketballshop.DTO.ProductDto;
import org.example.basketballshop.Models.Product;
import org.example.basketballshop.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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

    @GetMapping("/api/products/{id}")
    @ResponseBody
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(ProductDto.in(product));
    }

    @GetMapping("/products/size-statistics")
    public String getSizeStatistics(Model model) {
        var statistics = productService.getPopularSizesByProduct();
        model.addAttribute("statistics", statistics);
        return "products/size_statistics";
    }
}
