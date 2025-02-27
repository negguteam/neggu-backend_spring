package com.neggu.neggu.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties


@ConfigurationProperties(prefix = "firebase")
data class FirebaseProperties(
    var credential: String = ""
)
