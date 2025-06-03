package org.example.basketballshop.DTO.Forms;


import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class ProductForm {
   private String name;
    private String description;
    private BigDecimal price;
    private List<String> sizesInput;
    private MultipartFile[] images;
}
