package com.neggu.neggu.service.oauth.token

import com.neggu.neggu.api.GoogleOauthClient
import com.neggu.neggu.config.properties.GoogleOauthProperties
import com.neggu.neggu.exception.ErrorType
import com.neggu.neggu.exception.UnAuthorizedException
import com.neggu.neggu.model.oauth.OidcPublicKeys
import com.neggu.neggu.model.user.OidcUser
import org.springframework.stereotype.Component

@Component
class GoogleIdTokenResolver(
    private val googleOauthClient: GoogleOauthClient,
    private val googleOauthProperties: GoogleOauthProperties,
    private val idTokenProcessor: IdTokenProcessor,
) : OpenIdTokenResolver {

    override fun resolveIdToken(idToken: String): OidcUser {
        val oidcPublicKeys: OidcPublicKeys = googleOauthClient.getPublicKeys()
        try {
            return idTokenProcessor.process(
                idToken,
                oidcPublicKeys,
                googleOauthProperties.iss,
                googleOauthProperties.aud,
            )
        } catch (e: Exception) {
            throw UnAuthorizedException(ErrorType.INVALID_ID_TOKEN)
        }
    }
}