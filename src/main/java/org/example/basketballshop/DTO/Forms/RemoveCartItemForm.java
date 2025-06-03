package org.example.basketballshop.DTO.Forms;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RemoveCartItemForm {
    private Long itemId;
}
