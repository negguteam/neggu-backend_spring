package com.neggu.neggu.config

import com.neggu.neggu.config.LoggerConfig.log
import com.neggu.neggu.config.LoggerConfig.nError
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
        val encryptKey = environment.getProperty("encryptKey") ?: run {
            log.nError( "Encrypt Key is not set")
            throw IllegalArgumentException("Encrypt Key is not set")
        }

        val encryptor = StandardPBEStringEncryptor().apply {
            setPassword(encryptKey)
            setSaltGenerator(RandomSaltGenerator())
            setIvGenerator(NoIvGenerator())
        }
        return encryptor
    }
}