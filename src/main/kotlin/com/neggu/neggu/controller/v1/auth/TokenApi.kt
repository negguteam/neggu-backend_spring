package com.neggu.neggu.controller.v1.auth

import com.neggu.neggu.annotation.ApiErrorResponse
import com.neggu.neggu.annotation.ApiErrorResponses
import com.neggu.neggu.dto.user.TokenRequest
import com.neggu.neggu.dto.user.TokenResponse
import com.neggu.neggu.exception.ErrorType
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag

@Tag(name = "02. [인증] (토큰 필요 X)")
interface TokenApi {
    @Operation(summary = "토큰 재발급 API")
    @ApiResponse(responseCode = "200", description = "토큰 재발급 성공")
    @ApiErrorResponses(
        [
            ApiErrorResponse(ErrorType.INVALID_REFRESH_TOKEN, "해당 사용자의 RefreshToken이 존재하지 않을 시 에러입니다."),
            ApiErrorResponse(ErrorType.DUPLICATE_USER_LOGIN, "중복된 로그인 발생 시 에러입니다."),
        ],
    )
    fun reissueToken(tokenRequest: TokenRequest): TokenResponse
}