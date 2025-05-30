package org.example.basketballshop.Controllers;


import jakarta.validation.Valid;
import org.example.basketballshop.DTO.AddressDto;
import org.example.basketballshop.DTO.Forms.AddressForm;
import org.example.basketballshop.Services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping
    public ResponseEntity<AddressDto> postAddress(@Valid @RequestBody AddressForm addressForm) {
        AddressDto createdTask = addressService.addAddress(addressForm);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }


    @PutMapping("/{addressId}")
    public ResponseEntity<Void> updateAddress(@PathVariable Long addressId,
                                           @Valid @RequestBody AddressForm addressForm) {
        boolean isUpdated = addressService.updateAddress(addressId, addressForm);

        if (isUpdated) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();

    }


    @GetMapping("/{addressId}")
    public ResponseEntity<AddressDto> getAddress(@PathVariable Long addressId) {
        Optional<AddressDto> address = addressService.getAddress(addressId);

        return address.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long addressId) {
        boolean isDeleted = addressService.deleteAddress(addressId);
        if (isDeleted) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
