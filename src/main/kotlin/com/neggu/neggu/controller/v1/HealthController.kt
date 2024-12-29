package com.neggu.neggu.controller.v1

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController("api/v1/health")
class HealthController {

    @GetMapping("/")
    fun test(): String {
        return "Server is Working"
    }
}