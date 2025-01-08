package com.shsh.eureka_server.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;



@Data
@RequiredArgsConstructor
public class UserProfileDTO {

    private String id;


    private String username;

    private String email;

    private LocalDate dateOfBirth;

    private String descriptionOfProfile;
    private boolean isShshDeveloper = false;

    private LocalDateTime registrationDate = LocalDateTime.now();
    private LocalDateTime lastUpdated = LocalDateTime.now();

    private boolean isVerifiedEmail = false;

    @Enumerated(EnumType.STRING)
    private Gender gender;
    private boolean isActive = true;

    private String avatarUrl;

    private String chatWallpaperUrl;

    private boolean isPremium = false;

    private LocalDateTime premiumExpiresAt = null;
    private String nicknameEmoji;
}
