package com.neggu.neggu.exception


data class FieldErrorResponse(
    val fieldName: String,
    val message: String,
)