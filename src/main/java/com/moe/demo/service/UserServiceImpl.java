package com.moe.demo.service;

import com.moe.demo.dao.RoleRepository;
import com.moe.demo.dao.UserDao;
import com.moe.demo.entity.User;
import com.moe.demo.web.WebUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(
            UserDao userDao,
            RoleRepository roleRepository,
            BCryptPasswordEncoder passwordEncoder
    ) {
        this.userDao = userDao;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public void save(WebUser webUser) {
        User user = new User();

        //assign user details to user object
        user.setName(webUser.getName());
        user.setEmail(webUser.getEmail());
        user.setPassword(passwordEncoder.encode(webUser.getPassword()));
        user.setActive(true);

        //assign role
        user.setRoles(Collections.singletonList(roleRepository.findByName("ROLE_EMPLOYEE")));

        //save user in database
        userDao.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        System.out.println("Input email in service : " + email);

        User user = userDao.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("Invalid email or password!");
        }

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),//set email instead of username
                user.getPassword(),
                user.getRoles()
                        .stream()
                        .map(role ->
                                new SimpleGrantedAuthority(role.getName())
                        )
                        .collect(Collectors.toList()));
    }

}
