package org.example.basketballshop.DTO.Forms;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AddressForm {
    private String street;
    private String city;
    private String postalCode;
    private String country;
}
