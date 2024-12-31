package com.neggu.neggu.config

import com.neggu.neggu.config.LoggerConfig.Companion.log
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties
import org.jasypt.encryption.StringEncryptor
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor
import org.jasypt.iv.NoIvGenerator
import org.jasypt.salt.RandomSaltGenerator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment


@Configuration
@EnableEncryptableProperties
class JasyptConfig(
    private val environment: Environment,
) {

    @Bean("jasyptStringEncryptor")
    fun stringEncryptor(): StringEncryptor {
        log.info { "Encrypt Key: ${environment.getProperty("encryptKey")}" }
        val encryptor = StandardPBEStringEncryptor().apply {
            setPassword(environment.getProperty("encryptKey"))
            setSaltGenerator(RandomSaltGenerator())
            setIvGenerator(NoIvGenerator())
        }
        return encryptor
    }
}