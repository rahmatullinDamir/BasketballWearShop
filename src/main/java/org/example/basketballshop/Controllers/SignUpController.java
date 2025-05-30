package org.example.basketballshop.Controllers;

import org.example.basketballshop.DTO.Forms.UserForm;
import org.example.basketballshop.Services.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SignUpController {
    @Autowired
    private SignUpService signUpService;

    @GetMapping("/signUp")
    public String getSignUpPage() {
        return "sign_up_page";
    }

    @PostMapping("/signUp")
    public String signUp(UserForm userForm) {
        signUpService.signUp(userForm);
        return "redirect:/signIn";
    }
}
