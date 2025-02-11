package com.neggu.neggu.service.auth

import com.neggu.neggu.api.AppleOauthClient
import com.neggu.neggu.api.GoogleOauthClient
import com.neggu.neggu.api.KakaoOauthClient
import com.neggu.neggu.model.auth.OidcPublicKeys
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class PublicKeyCacheService(
    private val googleOauthClient: GoogleOauthClient,
    private val kakaoOauthClient: KakaoOauthClient,
    private val appleOauthClient: AppleOauthClient,
) {

    @Cacheable("googlePublicKeys")
    fun getGooglePublicKeys(): OidcPublicKeys {
        return googleOauthClient.getPublicKeys()
    }

    @Cacheable("kakaoPublicKeys")
    fun getKakaoPublicKeys(): OidcPublicKeys {
        return kakaoOauthClient.getPublicKeys()
    }

    @Cacheable("applePublicKeys")
    fun getApplePublicKeys(): OidcPublicKeys {
        return appleOauthClient.getPublicKeys()
    }
}