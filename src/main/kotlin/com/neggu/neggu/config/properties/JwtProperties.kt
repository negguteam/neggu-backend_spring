package com.neggu.neggu.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "auth.jwt")
data class JwtProperties(
    var access: TokenProperties,
    var refresh: TokenProperties,
) {

    data class TokenProperties(
        var secret: String,
        var expiry: Long,
    )
}