package org.example.basketballshop.Services;

import org.example.basketballshop.DTO.ProductDto;
import org.example.basketballshop.Models.Product;
import org.example.basketballshop.DTO.Forms.ProductForm;

import java.util.List;

public interface ProductService {
    Product getProductById(Long id);
    List<ProductDto> getAllProductsWithDiscount(int discount);
    boolean saveProduct(ProductForm productForm);
    void deleteProductById(Long id);

//    Product addImagesToProduct(Long productId, List<ImageInfo> imageInfos);
//    void deleteImageById(Long imageId);
//    void setMainImage(Long productId, Long imageId);

    List<Product> searchProductsByName(String keyword);
    List<Product> filterBySize(String size);
}