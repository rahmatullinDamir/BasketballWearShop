package org.example.basketballshop.Services;

import org.example.basketballshop.DTO.ProductDto;
import org.example.basketballshop.Models.CartItem;
import org.example.basketballshop.Models.Product;
import org.example.basketballshop.DTO.Forms.ProductForm;

import java.util.List;
import java.util.Map;

public interface ProductService {
    Product getProductById(Long id);
    List<ProductDto> getAllProductsWithDiscount(int discount);
    boolean saveProduct(ProductForm productForm);
    void deleteProductById(Long id);
    List<CartItem> getCartProductsWithDiscount(int discount, List<CartItem> items);
    List<Map<String, Object>> getPopularSizesByProduct();
}