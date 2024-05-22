package com.ssafy.backend_dummy.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class SwaggerConfig {
    @Bean
    fun openAPI(): OpenAPI {
        val apiKey = SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .`in`(SecurityScheme.In.HEADER)
                .name("Authorization")

        val securityRequirement = SecurityRequirement()
                .addList("Bearer Token")

        return OpenAPI()
                .components(Components().addSecuritySchemes("Bearer Token", apiKey))
                .addSecurityItem(securityRequirement)

    }

    private fun apiInfo(): Info {
        return Info()
                .title("Smudy")
                .description("베른에 돌 던지지 말아주세요")
                .version("1.0.0")
    }
}
