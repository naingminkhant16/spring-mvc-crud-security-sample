package com.moe.demo.dao;

import com.moe.demo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query(value = "SELECT * FROM roles WHERE name = ?1", nativeQuery = true)
    Role findByName(String name);
}
