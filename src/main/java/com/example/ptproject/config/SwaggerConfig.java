package com.example.ptproject.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI gymApiOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("헬스장 PT 예약 API")
                        .description("트레이너 등록·조회와 PT 예약 등록·조회·수정·취소를 제공하는 REST API")
                        .version("v1"));
    }
}
