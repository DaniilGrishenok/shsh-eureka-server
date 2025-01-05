package com.shsh.eureka_server.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
    @GetMapping("/login")
    public String login() {
        return "loginq";  // Имя шаблона для страницы логина
    }
}
