package com.neggu.neggu.api

import com.neggu.neggu.model.oauth.OidcPublicKeys
import org.springframework.web.bind.annotation.GetMapping

interface AppleOauthClient {

    @GetMapping("/auth/keys")
    fun getPublicKeys(): OidcPublicKeys
}