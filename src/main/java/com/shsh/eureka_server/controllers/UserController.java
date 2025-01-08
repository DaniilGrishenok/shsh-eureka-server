package com.shsh.eureka_server.controllers;

import com.shsh.eureka_server.dto.UserProfileDTO;
import com.shsh.eureka_server.services.RedisOnlineUserService;
import com.shsh.eureka_server.services.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/stats")
@RequiredArgsConstructor
public class UserController {

    private final RedisOnlineUserService redisOnlineUserService;
    private final UserProfileService userProfileService;
    @GetMapping("/user/profiles")
    public String getUserProfiles(Model model) {
        List<UserProfileDTO> profiles = userProfileService.getAllProfiles();
        model.addAttribute("profiles", profiles);

        return "users";
    }

    @GetMapping("/online-users/count")
    public long getOnlineUserCount() {
        return redisOnlineUserService.getOnlineUserCount();
    }

    @GetMapping("/online-users")
    public Set<String> getAllOnlineUsers() {
        return redisOnlineUserService.getAllOnlineUsers();
    }

}
