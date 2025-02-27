package com.neggu.neggu.controller.v1.health

import com.neggu.neggu.dto.fcm.FcmMessageRequestDTO
import com.neggu.neggu.exception.ErrorType
import com.neggu.neggu.exception.ServerException
import com.neggu.neggu.service.fcm.FcmService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/health")
class HealthController(
    private val fcmService: FcmService
) : HealthApi {

    @GetMapping("/")
    @Operation(summary = "Test API Health Check")
    override fun health(): HealthApi.HealthResponse {
        return HealthApi.HealthResponse(status = "Server is Working")
    }

    @GetMapping("/fcm")
    fun testFcm(
        @RequestParam token: String,
        @RequestParam title: String,
        @RequestParam body: String
    ): String{
        return fcmService.sendMessage(FcmMessageRequestDTO(token, title, body))
    }

    @GetMapping("/unknown-error")
    override fun unKnownError(): HealthApi.HealthResponse {
        val a: String? = null
        return HealthApi.HealthResponse(status = a!!)
    }

    @GetMapping("/known-error")
    override fun knownError(): HealthApi.HealthResponse {
        throw ServerException(ErrorType.UserNotFound)
    }
}