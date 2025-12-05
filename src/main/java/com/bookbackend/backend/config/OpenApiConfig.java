package com.bookbackend.backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Book Backend API 문서")
                        .version("1.0.0")
                        .description("도서 관리 백엔드 서비스의 REST API 문서입니다.")
                );
    }
}
