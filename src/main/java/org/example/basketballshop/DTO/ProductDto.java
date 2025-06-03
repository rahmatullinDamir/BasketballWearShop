package org.example.basketballshop.DTO;


import lombok.Builder;
import lombok.Data;
import org.example.basketballshop.Models.ImageInfo;
import org.example.basketballshop.Models.Product;
import org.example.basketballshop.Models.ProductSize;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private List<String> sizes;
    private int discount;
    private BigDecimal discountPrice;
    private List<ImageInfo> images;


    public static ProductDto in(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .sizes(product.getSizes().stream().map(ProductSize::getSize).toList())
                .images(product.getImages())
                .build();
    }

    public static List<ProductDto> from(List<Product> products) {
        return products.stream().map(ProductDto::in).toList();
    }
}
