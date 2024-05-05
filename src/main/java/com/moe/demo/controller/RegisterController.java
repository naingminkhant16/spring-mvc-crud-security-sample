package com.moe.demo.controller;

import com.moe.demo.entity.User;
import com.moe.demo.service.UserService;
import com.moe.demo.web.WebUser;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private final UserService userService;

    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @GetMapping("/form")
    public String showForm(Model model) {
        model.addAttribute("webUser", new WebUser());
        return "register/form";
    }

    @PostMapping("/process")
    public String processRegistration(
            @Valid @ModelAttribute("webUser") WebUser webUser,
            BindingResult bindingResult,
            Model model,
            HttpSession session
    ) {

        if (bindingResult.hasErrors()) {
            return "register/form";
        }

        String email = webUser.getEmail();
        //check if email is already used
        User existing = userService.findByEmail(email);
        if (existing != null) {
            model.addAttribute("webUser", new WebUser());
            model.addAttribute("emailAlreadyUsedError", "Email is already used!");
            return "register/form";
        }

        //store in database
        userService.save(webUser);

        session.setAttribute("auth", webUser);
        return "register/confirmation";

    }

}
