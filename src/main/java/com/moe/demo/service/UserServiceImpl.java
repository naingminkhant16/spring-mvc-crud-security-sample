package com.moe.demo.service;

import com.moe.demo.dao.UserDao;
import com.moe.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        System.out.println("Input email in service : " + email);

        User user = userDao.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("Invalid email or password!");
        }

        return new org.springframework.security.core.userdetails.User(
                user.getName(),
                user.getPassword(),
                user.getRoles()
                        .stream()
                        .map(role ->
                                new SimpleGrantedAuthority(role.getName())
                        )
                        .collect(Collectors.toList()));
    }

}
