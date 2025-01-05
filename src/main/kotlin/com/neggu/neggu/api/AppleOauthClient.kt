package com.neggu.neggu.api

import com.neggu.neggu.model.auth.OidcPublicKeys
import org.springframework.web.service.annotation.GetExchange

interface AppleOauthClient {

    @GetExchange("/auth/keys")
    fun getPublicKeys(): OidcPublicKeys
}