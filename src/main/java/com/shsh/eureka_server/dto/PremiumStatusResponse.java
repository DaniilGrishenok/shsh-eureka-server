package com.shsh.eureka_server.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PremiumStatusResponse {
    private boolean isPremium;
    private LocalDateTime premiumExpiresAt;
}