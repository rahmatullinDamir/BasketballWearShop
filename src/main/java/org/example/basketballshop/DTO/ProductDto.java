package org.example.basketballshop.DTO;


import lombok.Builder;
import lombok.Data;
import org.example.basketballshop.Models.ImageInfo;
import org.example.basketballshop.Models.Product;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class ProductDto {
    private String name;
    private String description;
    private BigDecimal price;
    private String sizes;
    private int discount;
    private BigDecimal discountPrice;
    private List<ImageInfo> images;


    public static ProductDto in(Product product) {
        return ProductDto.builder()
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .sizes(product.getSizes())
                .images(product.getImages())
                .build();
    }

    public static List<ProductDto> from(List<Product> products) {
        return products.stream().map(ProductDto::in).toList();
    }
}
