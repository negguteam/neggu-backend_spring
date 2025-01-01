package com.neggu.neggu.api

import com.neggu.neggu.config.properties.OpenIdConnectProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.web.client.RestClient
import org.springframework.web.client.support.RestClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory

@Configuration
class ApiClientConfig(
    private val openIdConnectProperties: OpenIdConnectProperties
) {

    @Bean
    fun appleOauthClient(): AppleOauthClient =
        registerApiClient<AppleOauthClient>(
            baseUrl = openIdConnectProperties.apple,
        )

    @Bean
    fun kakaoOauthClient(): KakaoOauthClient =
        registerApiClient<KakaoOauthClient>(
            baseUrl = openIdConnectProperties.kakao,
        )

    @Bean
    fun googleOauthClient(): GoogleOauthClient =
        registerApiClient<GoogleOauthClient>(
            baseUrl = openIdConnectProperties.google,
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