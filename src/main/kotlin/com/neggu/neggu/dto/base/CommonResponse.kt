package com.neggu.neggu.dto.base

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import io.swagger.v3.oas.annotations.media.Schema

@JsonPropertyOrder("isSuccess", "code", "message", "result")
@Schema(description = "기본 응답")
data class CommonResponse<T>(
    @Schema(description = "응답 메시지", required = true, example = "요청에 성공하였습니다.")
    val message: String,
    @Schema(description = "응답 코드", required = true, example = "200")
    val code: String,
    @Schema(description = "응답 결과", required = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val data: T?
) {

    companion object {

        fun <T> onSuccess(data: T?): CommonResponse<T> { // data를 nullable로 변경
            return CommonResponse(
                message = "요청에 성공하였습니다.",
                code = "200",
                data = data
            )
        }

        fun <T> onFailure(code: String, message: String, data: T?): CommonResponse<T> { // data를 nullable로 변경
            return CommonResponse(
                message = message,
                code = code,
                data = data
            )
        }
    }
}
