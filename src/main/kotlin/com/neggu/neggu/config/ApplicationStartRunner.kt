package com.neggu.neggu.config

import com.neggu.neggu.config.LoggerConfig.log
import com.neggu.neggu.config.LoggerConfig.nInfo
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component

@Component
class ApplicationStartRunner(
    private val environment: Environment
) : ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        val profile = environment.activeProfiles.joinToString()
        log.nInfo("Server Application Start at $profile")
    }
}