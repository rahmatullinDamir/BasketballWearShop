package org.example.basketballshop.Controllers;

import jakarta.validation.Valid;
import org.example.basketballshop.DTO.GiftCertificateDto;
import org.example.basketballshop.DTO.Forms.GiftCertificateForm;
import org.example.basketballshop.Services.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/gift-certificates")
public class GiftCertificateController {

    @Autowired
    private GiftCertificateService giftCertificateService;

    @GetMapping
    public String getCertificatesPage(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        List<GiftCertificateDto> purchasedCertificates = 
            giftCertificateService.getPurchasedCertificates(principal.getName());
        List<GiftCertificateDto> usedCertificates = 
            giftCertificateService.getUsedCertificates(principal.getName());

        model.addAttribute("purchasedCertificates", purchasedCertificates);
        model.addAttribute("usedCertificates", usedCertificates);
        model.addAttribute("giftCertificateForm", new GiftCertificateForm());

        return "gift_certificates";
    }

    @PostMapping("/purchase")
    @ResponseBody
    public ResponseEntity<?> purchaseCertificate(
            @Valid @RequestBody GiftCertificateForm form,
            Principal principal) {
        try {
            GiftCertificateDto certificate = 
                giftCertificateService.purchaseCertificate(form, principal.getName());
            return ResponseEntity.ok(certificate);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/validate/{code}")
    @ResponseBody
    public ResponseEntity<?> validateCertificate(@PathVariable String code) {
        try {
            GiftCertificateDto certificate = giftCertificateService.validateCertificate(code);
            return ResponseEntity.ok(certificate);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/apply")
    public String applyCertificate(
            @RequestParam String code,
            @RequestParam Long orderId,
            Principal principal,
            RedirectAttributes redirectAttributes) {
        try {
            giftCertificateService.applyCertificateToOrder(code, principal.getName(), orderId);
            redirectAttributes.addFlashAttribute("success", 
                "Сертификат успешно применен к заказу");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/orders/" + orderId;
    }
}
