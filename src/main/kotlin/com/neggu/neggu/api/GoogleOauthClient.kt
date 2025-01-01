package com.neggu.neggu.api

import com.neggu.neggu.model.auth.OidcPublicKeys
import org.springframework.web.service.annotation.GetExchange

interface GoogleOauthClient {

    @GetExchange("/oauth2/v3/certs")
    fun getPublicKeys(): OidcPublicKeys
}