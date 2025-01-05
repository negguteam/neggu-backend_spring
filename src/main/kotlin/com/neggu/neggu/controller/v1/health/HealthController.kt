package com.neggu.neggu.controller.v1.health

import com.neggu.neggu.config.LoggerConfig.log
import com.neggu.neggu.config.LoggerConfig.nDebug
import com.neggu.neggu.config.LoggerConfig.nError
import com.neggu.neggu.config.LoggerConfig.nInfo
import com.neggu.neggu.exception.ErrorType
import com.neggu.neggu.exception.NotFoundException
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/health")
class HealthController : HealthApi {

    @GetMapping("/")
    @Operation(summary = "Test API Health Check")
    override fun health(): HealthApi.HealthResponse {
        return HealthApi.HealthResponse(status = "Server is Working")
    }

    @GetMapping("/unknown-error")
    override fun unKnownError(): HealthApi.HealthResponse {
        throw RuntimeException("Unknown Error Message")
    }

    @GetMapping("/known-error")
    override fun knownError(): HealthApi.HealthResponse {
        repeat(5) {
            log.nDebug("test Debug Message")
            log.nInfo("test Debug Message")
            log.nError("test Debug Message")
        }
        throw NotFoundException(ErrorType.USER_NOT_FOUND)
    }
}