package com.example.two.taskmanager.controllers;


import com.example.two.taskmanager.models.Task;
import com.example.two.taskmanager.models.User;
import com.example.two.taskmanager.models.UserDetails;
import com.example.two.taskmanager.repositories.UserRepository;
import com.example.two.taskmanager.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAuthority('USER')")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private UserServices userServices;

    @GetMapping
    public String userIndex(Model model){
        return "user/dashboard";
    }
    @GetMapping("/dashboard")
    public String userDashboard(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails =(UserDetails) authentication.getPrincipal();
        User currentUser = userDetails.getUser();
        Task task = new Task();
        task.setUser(currentUser);
        model.addAttribute("task",task);
        return "user/dashboard";
    }
    @GetMapping("/profile")
    public String getProfilePage(Model model, Principal principal){
        String username = principal.getName();
        User user = userRepository.findByEmail(username);
        model.addAttribute("user",user);
        return "user/profile";
    }

    @PostMapping("profile/update")
    public String updateProfile(@ModelAttribute User user){
        if (user.getId()==null){
            throw new IllegalArgumentException("The given id must not be null");
        }
        userServices.updateUser(user);
        return "redirect:/user/profile";
    }
}
