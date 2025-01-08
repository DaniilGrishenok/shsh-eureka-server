package com.shsh.eureka_server.controllers;

import com.shsh.eureka_server.dto.PremiumDTO;
import com.shsh.eureka_server.dto.PremiumStatusResponse;
import com.shsh.eureka_server.dto.UserProfileDTO;
import com.shsh.eureka_server.services.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Controller
@RequiredArgsConstructor
public class AdminController {
    private final UserProfileService userProfileService;
    @GetMapping("/login")
    public String login() {
        return "loginq";  // Имя шаблона для страницы логина
    }
    @GetMapping("/users")
    public String getUserProfiles(Model model) {
        List<UserProfileDTO> profiles = userProfileService.getAllProfiles();
        model.addAttribute("profiles", profiles);

        return "users";
    }
    @GetMapping("/users/{id}")
    public String getUserProfile(@PathVariable String id, Model model) {
        UserProfileDTO profile = userProfileService.getProfile(id);
        List<UserProfileDTO> profiles = userProfileService.getAllProfiles();
        model.addAttribute("profiles", profiles);
        model.addAttribute("profile", profile);
        return "users"; // Возвращаем тот же шаблон со списком пользователей
    }
    @GetMapping("/users/{id}/premium")
    public String editPremiumStatus(@PathVariable String id, Model model) {
        UserProfileDTO profile = userProfileService.getProfile(id);

        // Форматирование даты истечения премиум статуса
        if (profile.getPremiumExpiresAt() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm", Locale.forLanguageTag("ru"));
            String formattedDate = profile.getPremiumExpiresAt()
                    .atZone(ZoneId.of("Europe/Moscow"))
                    .format(formatter);
            model.addAttribute("premiumExpiresAtFormatted", formattedDate);
        }

        model.addAttribute("profile", profile);
        return "editPremium"; // Шаблон для редактирования премиум-статуса
    }

    @PostMapping("/users/{id}/premium")
    public String updatePremiumStatus(@PathVariable String id, @RequestParam String changePremium, Model model) {
        // Формируем JSON-объект с нужным полем
        PremiumDTO premiumDTO = new PremiumDTO();
        premiumDTO.setChangePremium(changePremium);
        UserProfileDTO profile = userProfileService.getProfile(id);
        // Отправляем запрос на обновление премиум статуса
        PremiumStatusResponse response = userProfileService.updatePremium(id, changePremium);

        // Проверка на null, если ответ от сервера не получен
        if (response == null) {
            model.addAttribute("updateStatus", "Ошибка при обновлении премиум статуса.");
        } else {
            model.addAttribute("updateStatus", response.isPremium() ? "Премиум статус обновлен." : "Премиум статус не обновлен.");
        }
        if (profile.getPremiumExpiresAt() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm", Locale.forLanguageTag("ru"));
            String formattedDate = profile.getPremiumExpiresAt()
                    .atZone(ZoneId.of("Europe/Moscow"))
                    .format(formatter);
            model.addAttribute("premiumExpiresAtFormatted", formattedDate);
        }
        model.addAttribute("profile",profile );
        return "editPremium"; // Возвращаем форму с обновленным статусом
    }



}
