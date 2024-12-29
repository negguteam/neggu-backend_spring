package com.neggu.neggu.controller.v1

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
@Tag(name = "99. Helath 체크 API")
class HealthController {

    @GetMapping("/health")
    @Operation(summary = "Test API Health Check")
    fun health(): String {
        return "Server is Working"
    }
}