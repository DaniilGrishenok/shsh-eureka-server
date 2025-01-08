package com.shsh.eureka_server.services;

import com.shsh.eureka_server.dto.PremiumDTO;
import com.shsh.eureka_server.dto.PremiumStatusResponse;
import com.shsh.eureka_server.dto.UserProfileDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Service
public class UserProfileService {


    private String userProfileServiceUrl = "http://USER-PROFILE-SERVICE";

    private final RestTemplate restTemplate;

    public UserProfileService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<UserProfileDTO> getAllProfiles() {
        String url = userProfileServiceUrl + "/user/profile/allProfiles";
        List<UserProfileDTO> profiles = restTemplate.exchange(
                url, HttpMethod.GET, null, new ParameterizedTypeReference<List<UserProfileDTO>>() {}).getBody();

        // Логирование
        System.out.println("Полученные профили: " + profiles);

        return profiles;
    }
    public UserProfileDTO getProfile(String userId) {
        String url = userProfileServiceUrl + "/user/profile/"+userId;
        UserProfileDTO profile = restTemplate.exchange(
                url, HttpMethod.GET, null, new ParameterizedTypeReference<UserProfileDTO>() {}).getBody();

        System.out.println("Полученные профиль: " + profile);

        return profile;
    }
    public PremiumStatusResponse updatePremium(String userId, String changePremium) {
        String url = userProfileServiceUrl + "/user/profile/" + userId + "/premium";

        // Создаем объект PremiumDTO с изменением статуса
        PremiumDTO premiumDTO = new PremiumDTO();
        premiumDTO.setChangePremium(changePremium);

        // Создаем заголовки для запроса с Content-Type: application/json
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Создаем HttpEntity с premiumDTO и заголовками
        HttpEntity<PremiumDTO> requestEntity = new HttpEntity<>(premiumDTO, headers);

        try {
            // Отправляем запрос с использованием exchange
            ResponseEntity<PremiumStatusResponse> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    PremiumStatusResponse.class
            );

            // Логирование успешного ответа
            PremiumStatusResponse response = responseEntity.getBody();
            System.out.println("Премиум статус обновлен: " + response);

            return response;
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            // Логирование ошибки
            System.out.println("Ошибка при обновлении премиум статуса: " + e.getMessage());
            return null; // или можно вернуть какой-то другой статус ошибки
        } catch (Exception e) {
            // Логирование общей ошибки
            System.out.println("Неизвестная ошибка: " + e.getMessage());
            return null; // или обработка ошибки другим способом
        }
    }





}
