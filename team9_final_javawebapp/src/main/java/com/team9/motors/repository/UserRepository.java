package com.team9.motors.repository;

import com.team9.motors.model.Role;
import com.team9.motors.model.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User,Integer> {
    public User findUserById(int id);
    public User findByUsername(String username);
    public List<User> findUsersByRole(Role role);
}
