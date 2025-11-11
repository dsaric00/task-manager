package com.example.two.taskmanager.controllers;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import com.example.two.taskmanager.models.User;
import com.example.two.taskmanager.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import javax.validation.Valid;
import java.util.List;

@Controller
public class AdminController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/admin")
    public String listUsers (Model model){
        List<User> users = userRepository.findAll();
        model.addAttribute("users",users);
        return "admin/index";
    }

    @GetMapping("/admin/add")
    public String showAddUserForm(Model model){
        model.addAttribute("user",new User());
        return "admin/add";
    }

    @PostMapping("/admin/add")
    public String addUser(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "admin/add";
        }else{
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String passwordEncode = encoder.encode(user.getPassword());
            user.setPassword(passwordEncode);
            user.setPasswordConfirm(passwordEncode);
            userRepository.save(user);
            return "redirect:/admin";
        }
    }

    @PostMapping("/admin/delete/{userId}")
    public String deleteUser(@PathVariable Long userId){
        userRepository.deleteById(userId);
        return "redirect:/admin";

    }

    @GetMapping("/admin/edit{userId}")
    public String showEditUserForm(@PathVariable Long userId, Model model){
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new IllegalArgumentException("Invalid user ID: "+userId));
        model.addAttribute("user",user);
        return "admin/edit";
    }

    @PostMapping("/admin/edit/{userId}")
    public String updateUser(@PathVariable Long userId, @ModelAttribute User user, Model model){
        User existingUser =userRepository.findById(userId)
                .orElseThrow(()-> new IllegalArgumentException("Invalid user ID: "+ userId));
        existingUser.setName(user.getName());
        existingUser.setSurname(user.getSurname());
        existingUser.setEmail(user.getEmail());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = encoder.encode(user.getPassword());
        existingUser.setPassword(password);
        existingUser.setPasswordConfirm(password);
        existingUser.setRoles(user.getRoles());
        userRepository.save(existingUser);
        return "redirect:/admin";
    }
}
