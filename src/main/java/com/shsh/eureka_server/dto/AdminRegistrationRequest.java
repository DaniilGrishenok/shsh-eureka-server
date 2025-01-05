package com.shsh.eureka_server.dto;

import lombok.Data;

@Data
public class AdminRegistrationRequest {
    private String username;
    private String password;
    private String confirmationWord;
}
