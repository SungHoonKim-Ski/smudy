package com.ssafy.backend_dummy.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class SwaggerConfig {
    @Bean
    fun openAPI(): OpenAPI {
        //        String jwt = "JWT";
        //        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwt);
        //        Components components = new Components().addSecuritySchemes(jwt, new SecurityScheme()
        return OpenAPI()
                .components(Components())
                .info(apiInfo())
        //                .addSecurityItem(securityRequirement)
        //                .components(components);
    }

    private fun apiInfo(): Info {
        return Info()
                .title("Smudy")
                .description("베른에 돌 던지지 말아주세요")
                .version("1.0.0")
    }
}
