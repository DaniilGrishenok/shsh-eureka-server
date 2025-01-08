package com.shsh.eureka_server.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/adminPanel")
@RequiredArgsConstructor
public class AdminPanelController {

    @GetMapping
    public String showAdminPanel() {
        return "admin-panel";
    }
}