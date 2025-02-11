package com.neggu.neggu.service.auth.token

import com.neggu.neggu.config.properties.KakaoOauthProperties
import com.neggu.neggu.exception.ErrorType
import com.neggu.neggu.exception.UnAuthorizedException
import com.neggu.neggu.model.auth.OauthProvider
import com.neggu.neggu.model.auth.OidcPublicKeys
import com.neggu.neggu.model.auth.OidcUser
import com.neggu.neggu.service.auth.PublicKeyCacheService
import org.springframework.stereotype.Component

@Component
class KakaoIdTokenResolver(
    private val publicKeyCacheService: PublicKeyCacheService,
    private val kakaoOauthProperties: KakaoOauthProperties,
    private val idTokenProcessor: IdTokenProcessor,
) : OpenIdTokenResolver {

    override fun resolveIdToken(idToken: String): OidcUser {
        val oidcPublicKeys: OidcPublicKeys = publicKeyCacheService.getKakaoPublicKeys()
        try {
            return idTokenProcessor.process(
                OauthProvider.APPLE,
                idToken,
                oidcPublicKeys,
                kakaoOauthProperties.iss,
                kakaoOauthProperties.aud,
            )
        } catch (e: Exception) {
            throw UnAuthorizedException(ErrorType.InvalidIdToken)
        }
    }
}