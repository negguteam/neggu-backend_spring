package com.neggu.neggu.api

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.web.client.RestClient
import org.springframework.web.client.support.RestClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory

@Configuration
class ApiClientConfig {

    @Bean
    fun kakaoOauthClient(): KakaoOauthClient =
        registerApiClient<KakaoOauthClient>(
            baseUrl = "https://kauth.kakao.com",
        )

    @Bean
    fun googleOauthClient(): GoogleOauthClient =
        registerApiClient<GoogleOauthClient>(
            baseUrl = "https://www.googleapis.com",
        )

    private inline fun <reified T : Any> registerApiClient(
        baseUrl: String,
        defaultHeaders: HttpHeaders = HttpHeaders.EMPTY,
    ): T {
        val restClient = RestClient.builder()
            .baseUrl(baseUrl)
            .defaultHeaders { it.addAll(defaultHeaders) }
            .build()
        val adapter = RestClientAdapter.create(restClient)
        val factory = HttpServiceProxyFactory.builderFor(adapter).build()

        return factory.createClient(T::class.java)
    }
}