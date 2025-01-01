package com.neggu.neggu.controller.v1.user

import com.neggu.neggu.dto.user.IdTokenRequest
import com.neggu.neggu.dto.user.SocialLoginResponse
import com.neggu.neggu.dto.user.TokenResponse
import com.neggu.neggu.dto.user.UserRegisterRequest
import com.neggu.neggu.model.oauth.RegisterClaims
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag


@Tag(name = "02. [인증] (토큰 필요 X)")
interface AuthApi {

    @Operation(summary = "소셜 로그인 API")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "소셜 로그인 성공",
                content = [
                    Content(
                        schema = Schema(implementation = SocialLoginResponse.Success::class),
                    ),
                ],
            ),
            ApiResponse(
                responseCode = "200(pending)",
                description = "소셜 로그인 보류",
                content = [
                    Content(
                        schema = Schema(implementation = SocialLoginResponse.Pending::class),
                    ),
                ],
            ),
        ],
    )
    fun login(
        @Parameter(name = "provider", description = "소셜 로그인 제공자 설정(kakao, apple, google)", `in` = ParameterIn.PATH)
        provider: String,
        idTokenRequest: IdTokenRequest,
    ): SocialLoginResponse

    @Operation(
        summary = "사용자 등록 API (소셜 로그인 API 이후)",
        parameters = [
            Parameter(
                name = "RegisterToken",
                `in` = ParameterIn.HEADER,
                required = true,
                description = "사용자 등록에 필요한 토큰",
            ),
        ],
    )
    @ApiResponse(responseCode = "200", description = "사용자 정보 등록 성공")
    fun register(
        @Schema(hidden = true) registerClaims: RegisterClaims,
        userRegisterRequest: UserRegisterRequest,
    ): TokenResponse

}