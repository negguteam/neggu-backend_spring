package com.neggu.neggu

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class NegguApplication

fun main(args: Array<String>) {
	runApplication<NegguApplication>(*args)
}
