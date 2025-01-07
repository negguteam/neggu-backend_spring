package com.neggu.neggu.annotation

import com.neggu.neggu.exception.ErrorType
import kotlin.reflect.KClass


@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class ApiErrorResponse(
    val errorType: KClass<out ErrorType>,
    val description: String,
)