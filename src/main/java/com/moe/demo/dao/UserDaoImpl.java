package com.moe.demo.dao;

import com.moe.demo.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {
    private final EntityManager entityManager;

    @Autowired
    public UserDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public User findByEmail(String email) {

        System.out.println("Input email : " + email);

        TypedQuery<User> query = entityManager.createQuery("from User where email=:e and active=true", User.class);
        query.setParameter("e", email);

        User user = null;
        try {
            user = query.getSingleResult();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return user;
    }
}
