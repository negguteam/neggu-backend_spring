package com.neggu.neggu.api

import com.neggu.neggu.model.auth.OidcPublicKeys
import org.springframework.web.service.annotation.GetExchange

interface KakaoOauthClient {

    @GetExchange("/.well-known/jwks.json")
    fun getPublicKeys(): OidcPublicKeys
}