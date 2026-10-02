package org.example.basketballshop.services;

import org.example.basketballshop.dto.ProductDto;
import org.example.basketballshop.models.CartItem;
import org.example.basketballshop.dto.forms.ProductForm;

import java.util.List;
import java.util.Map;

public interface ProductService {
    ProductDto getProductById(Long id);
    List<ProductDto> getAllProductsWithDiscount(int discount);
    boolean saveProduct(ProductForm productForm);
    void deleteProductById(Long id);
    List<CartItem> getCartProductsWithDiscount(int discount, List<CartItem> items);
    List<Map<String, Object>> getPopularSizesByProduct();
}