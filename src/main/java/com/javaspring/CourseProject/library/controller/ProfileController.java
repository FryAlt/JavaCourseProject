package com.javaspring.CourseProject.library.controller;

import com.javaspring.CourseProject.library.entity.Order;
import com.javaspring.CourseProject.library.entity.User;
import com.javaspring.CourseProject.library.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String profile(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        String username = principal.getName();
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        List<Order> orders = userService.getOrders(username);

        model.addAttribute("user", user);
        model.addAttribute("orders", orders);

        return "profile/index";
    }

}