package com.neggu.neggu.config

import com.github.benmanes.caffeine.cache.Caffeine
import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.TimeUnit


@Configuration
class CacheConfig {

    @Bean
    fun publicKeyCacheManager(): CaffeineCacheManager {
        val cacheManager = CaffeineCacheManager("googlePublicKeys", "kakaoPublicKeys", "applePublicKeys")
        cacheManager.setCaffeine(
            Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .maximumSize(100)
        )
        return cacheManager
    }
}