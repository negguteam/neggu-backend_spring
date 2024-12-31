package com.neggu.neggu.annotation

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class ApiErrorResponses(
    val value: Array<ApiErrorResponse>,
)