package com.neggu.neggu.service.auth.token

import com.neggu.neggu.model.auth.OauthProvider
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component


@Component
class OpenIdTokenResolverSelector(
    private val applicationContext: ApplicationContext,
) {
    fun select(oauthProvider: OauthProvider): OpenIdTokenResolver {
        return when (oauthProvider) {
            OauthProvider.KAKAO -> applicationContext.getBean(KakaoIdTokenResolver::class.java)
            OauthProvider.GOOGLE -> applicationContext.getBean(GoogleIdTokenResolver::class.java)
            OauthProvider.APPLE ->  applicationContext.getBean(AppleIdTokenResolver::class.java)
        }
    }
}