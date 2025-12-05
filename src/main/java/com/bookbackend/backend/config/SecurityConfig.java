package com.bookbackend.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

/**
 * 🔐 SecurityConfig
 * ---------------------------------------------------------
 * 현재 개발 단계에서는 인증/인가를 전면 비활성화한 상태입니다.
 * - Swagger(OpenAPI UI) 자유 접근
 * - React 개발 서버(localhost:5173)에서 모든 API 호출 가능
 * - 이후 실제 인증 구현(JWT 등) 시 해당 설정을 수정 예정입니다.
 * 작성자: 서범수
 * 작성일: 2024-12-04
 * ---------------------------------------------------------
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth
                        // Swagger 허용
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()

                        // React 개발 서버에서 호출하는 API 모두 허용
                        .requestMatchers("/**").permitAll()

                        .anyRequest().permitAll() // 인증 완전 비활성화
                )

                // 인증 기능 완전 비활성화
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable);

        return http.build();
    }

    // CORS 설정
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(List.of(
                "http://localhost:5173",  // React 개발 서버
                "http://localhost:8080",  // Swagger
                "*"
        ));

        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);  // 필요 시 쿠키 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}
