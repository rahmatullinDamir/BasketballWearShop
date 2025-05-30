package org.example.basketballshop.DTO;

import lombok.Builder;
import lombok.Data;
import org.example.basketballshop.Models.Address;
import org.example.basketballshop.Models.Product;

import java.util.List;

@Builder
@Data
public class AddressDto {

    private String street;
    private String city;
    private String postalCode;
    private String country;


    public static AddressDto in(Address address) {
        return AddressDto.builder()
                .street(address.getStreet())
                .city(address.getCity())
                .postalCode(address.getPostalCode())
                .country(address.getCountry())
                .build();
    }

    public static List<AddressDto> from(List<Address> addresses) {
        return addresses.stream().map(AddressDto::in).toList();
    }
}
