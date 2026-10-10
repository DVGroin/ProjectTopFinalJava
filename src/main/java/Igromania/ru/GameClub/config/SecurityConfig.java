package Igromania.ru.GameClub.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // Включает аннотации @PreAuthorize для точечной проверки уровней админки в контроллерах
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Хеширование паролей по алгоритму BCrypt (Требование 3.1 ТЗ)
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1. Настройка доступов к ресурсам
                .authorizeHttpRequests(auth -> auth
                        // Статические файлы (стили, скрипты тем и JS-игры) доступны абсолютно всем
                        .requestMatchers("/css/**", "/js/**", "/static/**", "/favicon.ico").permitAll()

                        // Справочные общедоступные страницы (Требование 3.5 ТЗ)
                        .requestMatchers("/", "/catalog", "/menu", "/faq", "/contacts", "/contacts/feedback", "/tictactoe").permitAll()

                        // Встроенные игры в браузере (доступны гостям и пользователям)
                        .requestMatchers("/play/**").permitAll()

                        // Регистрация и вход
                        .requestMatchers("/login", "/register").permitAll()

                        // Вся зона /admin/** строго требует роль ROLE_ADMIN
                        // Конкретные уровни (1, 2, 3) мы дополнительно разграничим внутри контроллеров через @PreAuthorize
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        // Личный кабинет и создание бронирований — только для авторизованных игроков (ROLE_USER или ROLE_ADMIN)
                        .requestMatchers("/profile/**", "/booking/**").hasAnyRole("USER", "ADMIN")

                        // Любой другой запрос требует авторизации
                        .anyRequest().authenticated()
                )

                // 2. Настройка формы входа (Требование 3.1 ТЗ)
                .formLogin(form -> form
                        .loginPage("/login")                 // Наш кастомный HTML-шаблон авторизации
                        .defaultSuccessUrl("/", true)        // Перенаправление на главную после успешного входа
                        .failureUrl("/login?error=true")     // Ошибка при неверных данных
                        .permitAll()
                )

                // 3. Настройка выхода из системы
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout=true")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .permitAll()
                )

                // 4. Дополнительно: Хранение сессий и защита H2-Console (если потребуется заглянуть в БД через браузер)
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**", "/contacts/feedback", "/admin/**")
                )
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.sameOrigin()) // Разрешает H2 консоли открываться в iframe
                );

        return http.build();
    }
}
