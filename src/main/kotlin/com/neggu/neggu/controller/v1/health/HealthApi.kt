package com.neggu.neggu.controller.v1.health

import com.neggu.neggu.dto.user.SocialLoginResponse
import com.neggu.neggu.exception.ErrorResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag


@Tag(name = "99. [헬스 체크 - 테스트] (토큰 필요 X)")
interface HealthApi {

    @Operation(summary = "Health API")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "헬스체크 성공",
            ),
        ],
    )
    fun health(): HealthResponse

    @Operation(summary = "서버 에러를 반환하는 API")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "500",
                description = "예측하지 못한 서버 API 실패 시 500번대 에러를 반환합니다.",
                content = [
                    Content(
                        schema = Schema(implementation = ErrorResponse::class),
                    ),
                ],
            ),
        ],
    )
    fun unKnownError(): HealthResponse

    @Operation(summary = "요청 에러를 반환하는 API")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "400",
                description = "서버 API 실패 시 400번대 에러를 반환합니다.",
                content = [
                    Content(
                        schema = Schema(implementation = ErrorResponse::class),
                    ),
                ],
            ),
        ],
    )
    fun knownError(): HealthResponse

    data class HealthResponse(
        val status: String = "success",
    )
}