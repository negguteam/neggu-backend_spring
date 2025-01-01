package com.neggu.neggu.service.auth.token

import com.neggu.neggu.api.KakaoOauthClient
import com.neggu.neggu.config.properties.KakaoOauthProperties
import com.neggu.neggu.exception.ErrorType
import com.neggu.neggu.exception.UnAuthorizedException
import com.neggu.neggu.model.auth.OidcPublicKeys
import com.neggu.neggu.model.auth.OidcUser
import org.springframework.stereotype.Component

@Component
class KakaoIdTokenResolver(
    private val kakaoOauthClient: KakaoOauthClient,
    private val kakaoOauthProperties: KakaoOauthProperties,
    private val idTokenProcessor: IdTokenProcessor,
) : OpenIdTokenResolver {

    override fun resolveIdToken(idToken: String): OidcUser {
        val oidcPublicKeys: OidcPublicKeys = kakaoOauthClient.getPublicKeys()
        try {
            return idTokenProcessor.process(
                idToken,
                oidcPublicKeys,
                kakaoOauthProperties.iss,
                kakaoOauthProperties.aud,
            )
        } catch (e: Exception) {
            throw UnAuthorizedException(ErrorType.INVALID_ID_TOKEN)
        }
    }
}