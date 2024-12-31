package com.neggu.neggu.config

import com.neggu.neggu.config.properties.CorsProperties
import com.neggu.neggu.service.oauth.AuthInterceptor
import com.neggu.neggu.service.oauth.LoginUserArgumentResolver
import com.neggu.neggu.service.oauth.PendingUserArgumentResolver
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfig(
    private val authInterceptor: AuthInterceptor,
    private val loginUserArgumentResolver: LoginUserArgumentResolver,
    private val pendingUserArgumentResolver: PendingUserArgumentResolver,
    private val corsProperties: CorsProperties,
) : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(authInterceptor)
        super.addInterceptors(registry)
    }

    override fun addArgumentResolvers(argumentResolvers: MutableList<HandlerMethodArgumentResolver?>) {
        argumentResolvers.add(loginUserArgumentResolver)
        argumentResolvers.add(pendingUserArgumentResolver)
    }

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins(*corsProperties.allowedOrigin.toTypedArray())
            .allowedHeaders("*")
            .allowedMethods("HEAD", "GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
            .allowCredentials(true)
            .maxAge(corsProperties.maxAge)
    }
}