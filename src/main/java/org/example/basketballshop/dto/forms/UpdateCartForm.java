package org.example.basketballshop.dto.forms;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateCartForm {
    private Long itemId;
    private int quantity;
}