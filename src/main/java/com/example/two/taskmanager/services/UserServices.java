package com.example.two.taskmanager.services;

import com.example.two.taskmanager.models.User;
import com.example.two.taskmanager.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServices {

    @Autowired
    private UserRepository userRepository;

    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public void updateUser(User updateUser){
        if(updateUser.getId() == null){
            throw new IllegalArgumentException("The given id must not be null");
        }
        User existingUser =userRepository.findById(updateUser.getId())
                .orElseThrow(()-> new IllegalArgumentException("Invalid user ID"));
        existingUser.setName(updateUser.getName());
        existingUser.setSurname(updateUser.getSurname());
        existingUser.setEmail(updateUser.getEmail());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = encoder.encode(existingUser.getPassword());
        existingUser.setPassword(password);
        existingUser.setPasswordConfirm(password);
        userRepository.save(existingUser);
    }


}
