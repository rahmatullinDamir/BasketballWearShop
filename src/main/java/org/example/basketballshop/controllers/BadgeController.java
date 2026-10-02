package org.example.basketballshop.controllers;

import org.example.basketballshop.dto.forms.BadgeForm;
import org.example.basketballshop.services.BadgesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class BadgeController {

    @Autowired
    private BadgesService badgeService;

    @PostMapping("/badges/save")
    public String saveBadge(BadgeForm badgeForm, RedirectAttributes redirectAttributes) {

       boolean isBadgeAdded = badgeService.createBadgeWithIcon(badgeForm);
       if (isBadgeAdded) {
           redirectAttributes.addFlashAttribute("message", "Значок успешно добавлен!");
       }
       else {
           redirectAttributes.addFlashAttribute("message", "Значок не добавлен...");
       }

        return "redirect:/admin";
    }
}