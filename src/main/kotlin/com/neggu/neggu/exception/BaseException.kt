package com.neggu.neggu.exception

abstract class BaseException(
    val errorType: ErrorType,
) : RuntimeException(errorType.message)