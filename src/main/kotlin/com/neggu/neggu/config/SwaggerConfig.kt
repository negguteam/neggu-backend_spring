package com.neggu.neggu.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class OpenApiConfig {

    @Bean
    fun openAPI(): OpenAPI {
        val info = Info()
            .title("Neggu Rest API")
            .version("0.0.1")
            .description("If you find any errors or incorrect parts, please call : neggu.business@gmail.com")

        val headerAuth = SecurityScheme()
            .type(SecurityScheme.Type.APIKEY)
            .name("X-AUTH-TOKEN")
            .`in`(SecurityScheme.In.HEADER)

        val addSecurityItem = SecurityRequirement().apply {
            addList("X-AUTH-TOKEN")
        }

        return OpenAPI()
            .components(Components().addSecuritySchemes("X-AUTH-TOKEN", headerAuth))
            .addSecurityItem(addSecurityItem)
            .info(info)
    }
}
