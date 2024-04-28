package com.moe.demo.dao;

import com.moe.demo.entity.User;

public interface UserDao {
    User findByEmail(String email);
}
