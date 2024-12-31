package com.neggu.neggu.annotation

import com.neggu.neggu.exception.ErrorType

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class ApiErrorResponse(
    val errorType: ErrorType,
    val description: String,
)