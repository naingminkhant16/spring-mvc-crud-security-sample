package com.moe.demo.web;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;

public class WebUser {
    @NotNull(message = "Name is required.")
    @Size(min = 1, message = "Name is required.")
    private String name;

    @NotNull(message = "Email is required.")
    @Size(min = 1, message = "Email is required.")
    @Pattern(
            regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$",
            message = "Invalid Email Format."
    )
    private String email;

    @NotNull(message = "Password is required.")
    @Length(min = 6, max = 20, message = "Password length must be between 6 and 20.")
    private String password;

    public WebUser() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
