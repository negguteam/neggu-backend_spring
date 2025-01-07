package com.neggu.neggu.exception

import com.fasterxml.jackson.annotation.JsonInclude


@JsonInclude(JsonInclude.Include.NON_NULL)
data class ErrorResponse private constructor(
    val code: Int,
    val message: String,
) {

    companion object {

        fun from(
            errorType: ErrorType,
            message: String? = null,
        ): ErrorResponse = ErrorResponse(
            code = errorType.status.value(),
            message = message ?: errorType.message,
        )
    }
}