package com.shsh.eureka_server.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RedisOnlineUserService {

    private final RedisTemplate<String, String> redisTemplate;

    private static final String USER_STATUS_PREFIX = "user:";  // Префикс для ключей статуса пользователя

    /**
     * Получает количество онлайн-пользователей.
     *
     * @return Количество пользователей онлайн.
     */
    public long getOnlineUserCount() {
        // Получаем все ключи, начинающиеся с "user:" (это ключи статуса пользователей)
        Set<String> allUsers = redisTemplate.keys(USER_STATUS_PREFIX + "*");
        if (allUsers == null) {
            return 0;
        }

        long onlineCount = 0;

        // Проверяем каждый ключ, чтобы убедиться, что это статус пользователя, а не сессия
        for (String key : allUsers) {
            // Проверяем, что ключ относится к статусу пользователя (не к сессиям)
            if (key.endsWith(":sessions")) {
                continue;  // Пропускаем ключи, связанные с сессиями
            }

            String status = redisTemplate.opsForValue().get(key);  // Статус пользователя
            if ("online".equals(status)) {  // Статус "online"
                onlineCount++;
            }
        }

        return onlineCount;
    }

    /**
     * Проверяет, находится ли пользователь в списке онлайн.
     *
     * @param userId ID пользователя.
     * @return true, если пользователь онлайн.
     */
    public boolean isUserOnline(String userId) {
        String key = USER_STATUS_PREFIX + userId;
        String status = redisTemplate.opsForValue().get(key);  // Статус пользователя по его ID
        return "online".equals(status);  // Если статус "online", возвращаем true
    }

    /**
     * Получает список всех онлайн пользователей.
     *
     * @return Список ID пользователей.
     */
    public Set<String> getAllOnlineUsers() {
        Set<String> allUsers = redisTemplate.keys(USER_STATUS_PREFIX + "*");
        if (allUsers == null) {
            return Collections.emptySet();
        }

        Set<String> onlineUsers = new HashSet<>();
        for (String key : allUsers) {
            // Проверяем, что это ключ статуса пользователя
            if (key.endsWith(":sessions")) {
                continue;  // Пропускаем ключи сессий
            }

            String status = redisTemplate.opsForValue().get(key);  // Статус пользователя
            if ("online".equals(status)) {  // Если статус "online"
                onlineUsers.add(key.split(":")[1]);  // Извлекаем userId из ключа
            }
        }

        return onlineUsers;
    }
}

