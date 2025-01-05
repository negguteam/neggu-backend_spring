package com.neggu.neggu.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order

@Configuration
@EnableConfigurationProperties(SlackProperties::class)
@Order(Ordered.HIGHEST_PRECEDENCE)
class SlackConfig

@ConfigurationProperties(prefix = "logging.slack")
class SlackProperties(
    var postChatUrl: String = "",
    var postChatChannel: String = "",
    var token: String = "",
)

