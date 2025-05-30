package org.example.basketballshop.DTO.Forms;


import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Data
@Builder
public class ProductForm {
   private String name;
    private String description;
    private BigDecimal price;
    private String sizesInput;
    private MultipartFile[] images;
}
