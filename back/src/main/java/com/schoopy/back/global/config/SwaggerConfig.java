package com.schoopy.back.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Schoopy API 문서",
                version = "v1.0.0",
                description = "API 명세서입니다"
        )
)
public class SwaggerConfig {

}