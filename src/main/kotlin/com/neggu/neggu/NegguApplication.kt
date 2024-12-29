package com.neggu.neggu

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class NegguApplication

fun main(args: Array<String>) {
	runApplication<NegguApplication>(*args)
}
