package com.saurabh.ecommerce.controller;

import com.saurabh.ecommerce.entity.User;
import com.saurabh.ecommerce.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProfileController {

    private final UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    // Get currently logged-in user
    private User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        return userService.getUserByEmail(email);
    }

    // View Profile
    @GetMapping("/profile")
    public String profile(Model model) {

        model.addAttribute("user", getCurrentUser());

        return "profile";
    }

    // Edit Profile Page
    @GetMapping("/profile/edit")
    public String editProfile(Model model) {

        model.addAttribute("user", getCurrentUser());

        return "profile-edit";
    }

    // Update Profile
    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute("user") User updatedUser) {

        User existingUser = getCurrentUser();

        existingUser.setFullName(updatedUser.getFullName());
        existingUser.setPhone(updatedUser.getPhone());
        existingUser.setAddress(updatedUser.getAddress());

        // Keep existing values
        existingUser.setEmail(existingUser.getEmail());
        existingUser.setRole(existingUser.getRole());
        existingUser.setPassword(existingUser.getPassword());

        userService.saveUser(existingUser);

        return "redirect:/profile?success";
    }
}