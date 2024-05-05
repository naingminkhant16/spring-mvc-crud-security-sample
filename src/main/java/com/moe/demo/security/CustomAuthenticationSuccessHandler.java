package com.moe.demo.security;

import com.moe.demo.entity.User;
import com.moe.demo.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final UserService userService;

    public CustomAuthenticationSuccessHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {

        System.out.println("In customAuthenticationSuccessHandler");

        //use email instead of username - username is default by spring security
        String email = authentication.getName();
        System.out.println("Email = " + email);

        //find user by email
        User user = userService.findByEmail(email);

        //set user object in session
        HttpSession session = request.getSession();
        session.setAttribute("auth", user);

        //redirect to products page
        response.sendRedirect(request.getContextPath() + "/");
    }
}
