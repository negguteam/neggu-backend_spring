package com.neggu.neggu

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableTransactionManagement(proxyTargetClass = true)
@EnableScheduling
class NegguApplication

fun main(args: Array<String>) {
	runApplication<NegguApplication>(*args)
}
