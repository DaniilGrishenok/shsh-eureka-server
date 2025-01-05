package com.shsh.eureka_server.controllers;

import com.shsh.eureka_server.dto.AdminRegistrationRequest;
import com.shsh.eureka_server.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegistrationController {
    private final AdminService adminService;

    @GetMapping
    public String showRegistrationPage() {
        return "register"; // Возвращает HTML-страницу для регистрации
    }

    @PostMapping
    public String register(@ModelAttribute AdminRegistrationRequest request, Model model) {
        try {
            adminService.registerAdmin(request);
            return "redirect:/login";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }
}