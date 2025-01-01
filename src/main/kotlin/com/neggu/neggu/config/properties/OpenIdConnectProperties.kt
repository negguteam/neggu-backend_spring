package com.neggu.neggu.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "open-id-connect")
data class OpenIdConnectProperties(
    val kakao: String,
    val google: String,
    val apple: String
)
