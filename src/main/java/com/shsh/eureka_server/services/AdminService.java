package com.shsh.eureka_server.services;

import com.shsh.eureka_server.dto.AdminRegistrationRequest;
import com.shsh.eureka_server.models.Admin;
import com.shsh.eureka_server.repository.AdminRepository;
import com.shsh.eureka_server.repository.ConfirmationWordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;
    private final ConfirmationWordRepository confirmationWordRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerAdmin(AdminRegistrationRequest request) {
        // Проверка кодового слова
        if (!confirmationWordRepository.existsByWord(request.getConfirmationWord())) {
            throw new IllegalArgumentException("Invalid confirmation word.");
        }

        // Проверка на существование администратора
        if (adminRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Admin with this username already exists.");
        }

        // Сохранение нового администратора
        Admin admin = new Admin();
        admin.setUsername(request.getUsername());
        admin.setPassword(passwordEncoder.encode(request.getPassword()));
        adminRepository.save(admin);
    }
}
