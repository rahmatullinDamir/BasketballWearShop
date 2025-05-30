package org.example.basketballshop.Controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GiftCertificateController {
    @GetMapping("/giftCertificate")
    public String getGiftCertificatePage() {
        return "gift_certificate_page";
    }
}
