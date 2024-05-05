package com.moe.demo.service;

import com.moe.demo.entity.User;
import com.moe.demo.web.WebUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User findByEmail(String email);

    void save(WebUser webUser);
}
