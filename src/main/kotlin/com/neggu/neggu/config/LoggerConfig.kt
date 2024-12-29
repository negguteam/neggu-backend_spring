package com.neggu.neggu.config

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging


class LoggerConfig {
    companion object {
        inline val <reified T> T.log: KLogger
            get() = KotlinLogging.logger(T::class.java.name)
    }
}
