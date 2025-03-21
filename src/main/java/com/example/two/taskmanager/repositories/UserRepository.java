package com.example.two.taskmanager.repositories;

import com.example.two.taskmanager.models.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
    public User findByEmail(String email);

}
