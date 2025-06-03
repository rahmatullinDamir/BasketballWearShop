package org.example.basketballshop.Controllers;

import org.example.basketballshop.DTO.CartDto;
import org.example.basketballshop.DTO.CartItemDto;
import org.example.basketballshop.DTO.Forms.RemoveCartItemForm;
import org.example.basketballshop.DTO.Forms.UpdateCartForm;
import org.example.basketballshop.Models.Cart;
import org.example.basketballshop.Services.CartService;
import org.example.basketballshop.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<String> addToCart(@RequestParam Long productId,
                                            @RequestParam String size,
                                            @RequestParam(defaultValue = "1") int quantity
                                            ) {
        Long userId = userService.getUserFromSession().getId();
        System.out.println();
        cartService.addToCart(userId, productId, quantity, size);
        return ResponseEntity.ok("Товар добавлен в корзину");
    }
    @GetMapping("/count")
    @ResponseBody
    public ResponseEntity<Map<String, Integer>> getCartItemCount() {

        Long userId = userService.getUserFromSession().getId();

        int count = cartService.getQuantityOfCart(userId);

        Map<String, Integer> response = new HashMap<>();
        response.put("count", count);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/remove")
    @ResponseBody
    public ResponseEntity<String> removeItemFromCart(@RequestBody RemoveCartItemForm request) {
        cartService.removeItem(request.getItemId());
        return ResponseEntity.ok("Товар удален");
    }

    @GetMapping
    public String viewCart(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        CartDto cartDto = cartService.getCartWithDiscount(principal.getName());
        model.addAttribute("cart", cartDto);
        
        userService.isUserHaveAddress().ifPresent(addressDto -> 
            model.addAttribute("userAddress", addressDto)
        );
        
        return "cart_page";
    }

    @PostMapping("/update-quantity")
    @ResponseBody
    public ResponseEntity<CartDto> updateQuantityResponse(@RequestBody UpdateCartForm request) {
        System.out.println(request);
        Cart updatedCart = cartService.updateQuantity(request.getItemId(), request.getQuantity());
        CartDto cartDto = cartService.getCartWithDiscount(updatedCart.getUser().getEmail());

        List<CartItemDto> itemDtos = updatedCart.getItems().stream()
                .map(CartItemDto::in)
                .collect(Collectors.toList());

        CartDto responseDto = CartDto.builder()
                .items(itemDtos)
                .total(cartDto.getTotal())
                .discount(cartDto.getDiscount())
                .appliedCertificate(cartDto.getAppliedCertificate())
                .build();

        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/apply-certificate")
    @ResponseBody
    public ResponseEntity<?> applyGiftCertificate(@RequestParam String code, Principal principal) {
        try {
            cartService.applyGiftCertificate(code, principal.getName());
            CartDto updatedCart = cartService.getCartWithDiscount(principal.getName());
            return ResponseEntity.ok(updatedCart);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/remove-certificate")
    @ResponseBody
    public ResponseEntity<?> removeGiftCertificate(Principal principal) {
        try {
            cartService.removeGiftCertificate(principal.getName());
            CartDto updatedCart = cartService.getCartWithDiscount(principal.getName());
            return ResponseEntity.ok(updatedCart);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

