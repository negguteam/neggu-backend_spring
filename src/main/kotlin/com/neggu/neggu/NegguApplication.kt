package com.neggu.neggu

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableTransactionManagement(proxyTargetClass = true)
@EnableScheduling
@EnableCaching
class NegguApplication

fun main(args: Array<String>) {
	runApplication<NegguApplication>(*args)
}
