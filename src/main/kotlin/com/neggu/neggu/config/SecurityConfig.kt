package com.neggu.neggu.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource


@Configuration
class SecurityConfig {

    @Bean
    fun oauth2SecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .httpBasic { it.disable() }
            .csrf { it.disable() }
            .formLogin { it.disable() }
            .headers { it.frameOptions { it.disable() } }
            .cors { it.configurationSource(corsConfigurationSource()) } // CORS 설정 추가
            .build()
    }


    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf("https://appleid.apple.com") // 허용할 Origin
        configuration.allowedMethods = listOf("GET", "POST", "OPTIONS") // 허용할 HTTP 메서드
        configuration.allowedHeaders = listOf("*") // 허용할 요청 헤더
        configuration.allowCredentials = true // 인증 정보 허용

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration) // 모든 경로에 적용
        return source
    }
}
