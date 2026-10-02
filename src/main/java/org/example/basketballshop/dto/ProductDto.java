package org.example.basketballshop.dto;


import lombok.Builder;
import lombok.Data;
import org.example.basketballshop.models.ImageInfo;
import org.example.basketballshop.models.Product;
import org.example.basketballshop.models.ProductSize;

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
    private List<ImageInfoDto> images;
}
