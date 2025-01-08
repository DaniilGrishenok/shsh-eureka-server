package com.shsh.eureka_server.config;

import com.shsh.eureka_server.models.CustomUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailService customUserDetailService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeRequests(auth -> auth
                        // Открытые страницы
                        .requestMatchers("/register", "/login", "/", "/eureka").permitAll() // Регистрация и логин доступны без авторизации

                        // Страницы, требующие логина
                        .requestMatchers("/admin/**").hasAuthority("ADMIN") // Админ-панель доступна только администраторам
                        .requestMatchers("/users/**").authenticated() // Страница пользователей доступна только авторизованным
                        .requestMatchers("/users/{id}/premium").authenticated() // Страница премиум статуса доступна только авторизованным

                        // Все остальные страницы без авторизации
                        .anyRequest().permitAll()
                )
                .formLogin(form -> form
                        .loginPage("/login") // Явное указание на страницу логина
                        .defaultSuccessUrl("/adminPanel", true) // Перенаправление на админку после успешного входа
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/") // Перенаправление на главную страницу после выхода
                        .permitAll()
                );

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}