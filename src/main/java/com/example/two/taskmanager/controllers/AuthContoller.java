package com.example.two.taskmanager.controllers;

import com.example.two.taskmanager.models.Role;
import com.example.two.taskmanager.models.User;
import com.example.two.taskmanager.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class AuthContoller {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/auth/register")
    public String add(Model model){
        User user = new User();
        model.addAttribute("user",user);
        return "admin/register";
    }

    @PostMapping("/auth/register")
    public String newUser(@Valid User user, BindingResult result, Model model){
        boolean errors = result.hasErrors();
        if (errors){
            model.addAttribute("user",user);
            return "admin/register";
        }else{
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String passwordEncoded = encoder.encode(user.getPassword());
            user.setPassword(passwordEncoded);
            user.setPasswordConfirm(passwordEncoded);
            user.getRoles().add(Role.USER);
            userRepository.save(user);
            return "redirect:/auth/login";
        }
    }

    @GetMapping("/auth/login")
    public String login(Model model){
        model.addAttribute("user",new User());
        return "admin/login";
    }
}
