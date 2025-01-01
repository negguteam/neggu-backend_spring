package com.neggu.neggu.controller.exception

import com.neggu.neggu.config.LoggerConfig.Companion.log
import com.neggu.neggu.exception.BaseException
import com.neggu.neggu.exception.ErrorResponse
import com.neggu.neggu.exception.ErrorType
import org.springframework.boot.logging.LogLevel
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(Exception::class)
    private fun handleAllException(exception: Exception): ResponseEntity<ErrorResponse> {
        log.error { "${ExceptionSource.HTTP} Exception: $exception\n Detail : ${exception.stackTraceToString()}" }
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ErrorResponse.from(
                errorType = ErrorType.SERVER_ERROR,
                message = exception.message ?: "An unexpected error has occurred.",
            ))
    }

    @ExceptionHandler(BaseException::class)
    private fun handleBaseException(exception: BaseException): ResponseEntity<ErrorResponse> {
        val errorType = exception.errorType
        when (errorType.logLevel) {
            LogLevel.ERROR -> log.error { "${ExceptionSource.HTTP} Exception: ${errorType.message}, Exception: $exception Detail : ${exception.stackTraceToString()}" }
            LogLevel.WARN -> log.warn { "${ExceptionSource.HTTP} Exception: ${errorType.message}, Exception: $exception Detail : ${exception.stackTraceToString()}" }
            else -> log.info { "${ExceptionSource.HTTP} Exception: ${errorType.message}, Exception: $exception Detail : ${exception.stackTraceToString()}" }
        }

        return ResponseEntity
            .status(errorType.status)
            .body(ErrorResponse.from(errorType))
    }

//    @ExceptionHandler(MethodArgumentNotValidException::class)
//    fun handleArgumentValidationException(exception: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
//        val errorType = ErrorType.BAD_REQUEST
//
//        logException(errorType, exception, ExceptionSource.HTTP)
//
//        // bindingResult를 순회하며 errorArgumentMap을 채운다.
//        val fieldErrorList: List<FieldErrorResponse> =
//            exception.bindingResult.allErrors.reversed()
//                .mapNotNull { error ->
//                    val field = (error as? FieldError)?.field
//                    val message = error?.defaultMessage
//                    if (field != null && message != null) {
//                        FieldErrorResponse(field, message)
//                    } else {
//                        null
//                    }
//                }
//
//        // 가장 처음 발생한 bindingResult의 message를 예외의 message로 처리한다.
//        exception.bindingResult.allErrors.last().defaultMessage?.also {
//            errorType.message = it
//        }
//
//        return ResponseEntity
//            .status(exception.statusCode)
//            .body(ErrorResponse.from(errorType, fieldErrorList))
//    }
//
//    @ExceptionHandler(HttpMessageNotReadableException::class)
//    fun handleMessageNotReadableException(exception: HttpMessageNotReadableException): ResponseEntity<ErrorResponse> {
//        val errorType = ErrorType.HTTP_MESSAGE_NOT_READABLE
//
//        logException(errorType, exception, ExceptionSource.HTTP)
//
//        return ResponseEntity
//            .status(errorType.status)
//            .body(ErrorResponse.from(errorType))
//    }
//
//    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
//    fun handleMethodNotSupportedException(exception: HttpRequestMethodNotSupportedException): ResponseEntity<ErrorResponse> {
//        val errorType = ErrorType.HTTP_METHOD_NOT_SUPPORTED
//
//        logException(errorType, exception, ExceptionSource.HTTP)
//
//        return ResponseEntity
//            .status(errorType.status)
//            .body(ErrorResponse.from(errorType))
//    }
//
//    @ExceptionHandler(MissingPathVariableException::class)
//    fun handleMissingPathVariableException(exception: MissingPathVariableException): ResponseEntity<ErrorResponse> {
//        val errorType = ErrorType.PATH_VARIABLE_MISSING
//
//        logException(errorType, exception, ExceptionSource.HTTP)
//
//        return ResponseEntity
//            .status(errorType.status)
//            .body(ErrorResponse.from(errorType))
//    }
//
//    @ExceptionHandler(MissingServletRequestParameterException::class)
//    fun handleMissingServletRequestParameterException(exception: MissingServletRequestParameterException): ResponseEntity<ErrorResponse> {
//        val errorType = ErrorType.REQUEST_PARAMETER_MISSING
//
//        logException(errorType, exception, ExceptionSource.HTTP)
//
//        return ResponseEntity
//            .status(errorType.status)
//            .body(ErrorResponse.from(errorType))
//    }
//
//    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
//    fun handleArgumentTypeMismatchException(exception: MethodArgumentTypeMismatchException): ResponseEntity<ErrorResponse> {
//        val errorType = ErrorType.ARGUMENT_TYPE_MISMATCH
//
//        logException(errorType, exception, ExceptionSource.HTTP)
//
//        return ResponseEntity
//            .status(errorType.status)
//            .body(ErrorResponse.from(errorType))
//    }

    private enum class ExceptionSource {
        NEGGU,
        HTTP,
    }
}