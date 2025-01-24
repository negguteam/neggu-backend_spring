package com.neggu.neggu.exception

import org.springframework.boot.logging.LogLevel
import org.springframework.http.HttpStatus

sealed class ErrorType(
    val status: HttpStatus,
    val logLevel: LogLevel,
    var message: String = status.reasonPhrase,
) {

    class ServerError : ErrorType(HttpStatus.INTERNAL_SERVER_ERROR, LogLevel.ERROR, "서버 에러가 발생했습니다.") {

        companion object {
            fun of(exception: Throwable) = ServerError().apply {
                message = exception.toString()
            }
        }
    }

    // COMMON
    data object BadRequest : ErrorType(HttpStatus.BAD_REQUEST, LogLevel.DEBUG, "요청 파라미터가 올바르지 않습니다.")
    data object HttpMessageNotReadable : ErrorType(HttpStatus.BAD_REQUEST, LogLevel.DEBUG, "요청 형식이 올바르지 않습니다.")
    data object HttpMethodNotSupported : ErrorType(HttpStatus.METHOD_NOT_ALLOWED, LogLevel.DEBUG, "지원하지 않는 HTTP METHOD 입니다.")
    data object PathVariableMissing : ErrorType(HttpStatus.BAD_REQUEST, LogLevel.DEBUG, "경로 변수가 누락되었습니다.")
    data object RequestParameterMissing : ErrorType(HttpStatus.BAD_REQUEST, LogLevel.DEBUG, "요청 파라미터가 누락되었습니다.")
    data object ArgumentTypeMismatch : ErrorType(HttpStatus.BAD_REQUEST, LogLevel.DEBUG, "경로 변수 혹은 요청 파라미터 타입이 올바르지 않습니다.")

    // Image
    data object NotFoundImage : ErrorType(HttpStatus.NOT_FOUND, LogLevel.DEBUG, "존재하지 않는 이미지입니다.")
    data object ImageUploadFail : ErrorType(HttpStatus.INTERNAL_SERVER_ERROR, LogLevel.ERROR, "이미지 업로드에 실패했습니다.")

    // Location
    data object NotFoundLocation : ErrorType(HttpStatus.NOT_FOUND, LogLevel.DEBUG, "존재하지 않는 위치입니다.")
    data object LocationNameDuplicate : ErrorType(HttpStatus.NOT_FOUND, LogLevel.DEBUG, "이미 존재하는 위치명입니다.")
    data object LocationCountExceed : ErrorType(HttpStatus.BAD_REQUEST, LogLevel.DEBUG, "위치는 최대 3개까지만 등록 가능합니다.")

    // Auth
    data object Unauthorized : ErrorType(HttpStatus.UNAUTHORIZED, LogLevel.DEBUG, "인증되지 않은 사용자입니다.")
    data object TokenExpired : ErrorType(HttpStatus.UNAUTHORIZED, LogLevel.DEBUG, "토큰이 만료되었습니다.")
    data object InvalidJwtToken : ErrorType(HttpStatus.UNAUTHORIZED, LogLevel.DEBUG, "유효하지 않은 토큰입니다.")
    data object InvalidIdToken : ErrorType(HttpStatus.UNAUTHORIZED, LogLevel.WARN, "유효하지 않은 ID TOKEN입니다.")
    data object InvalidMatchingKey : ErrorType(HttpStatus.BAD_GATEWAY, LogLevel.WARN, "응답값과 매칭되는 키가 존재하지 않습니다.")
    data object InvalidRefreshToken : ErrorType(HttpStatus.UNAUTHORIZED, LogLevel.DEBUG, "유효하지 않은 RefreshToken입니다.")
    data object InvalidOAuthProvider : ErrorType(HttpStatus.BAD_REQUEST, LogLevel.DEBUG, "지원하지 않는 provider입니다.")
    data object DuplicateUserLogin : ErrorType(HttpStatus.UNAUTHORIZED, LogLevel.DEBUG, "중복된 로그인이 감지되었습니다.")

    // User
    data object UserNotFound : ErrorType(HttpStatus.NOT_FOUND, LogLevel.DEBUG, "존재하지 않은 사용자입니다.")
    data object DuplicateNickanme : ErrorType(HttpStatus.NOT_FOUND, LogLevel.ERROR, "중복된 닉네임입니다.")

    // Cloth, Lookbook
    data object NotFoundCloth : ErrorType(HttpStatus.NOT_FOUND, LogLevel.DEBUG, "존재하지 않는 옷입니다.")
    data object NotFoundLookBook : ErrorType(HttpStatus.NOT_FOUND, LogLevel.DEBUG, "존재하지 않는 룩북입니다.")

    // ObjectId
    data object InvalidObjectId : ErrorType(HttpStatus.BAD_REQUEST, LogLevel.DEBUG, "유효하지 않은 ObjectId입니다.")

    // CLIENT
    data object OpenApiCallException : ErrorType(HttpStatus.BAD_GATEWAY, LogLevel.WARN, "OpenAPI 호출에 실패했습니다.")
    data object PushApiCallException : ErrorType(HttpStatus.BAD_GATEWAY, LogLevel.WARN, "Push Message 전송에 실패했습니다.")

    // REGION
    data object NotFoundRegion : ErrorType(HttpStatus.NOT_FOUND, LogLevel.DEBUG, "존재하지 않는 지역번호입니다.")

    // Redis
    data object NotFoundRedisKey : ErrorType(HttpStatus.BAD_REQUEST, LogLevel.DEBUG, "해당 Key가 존재하지 않습니다.")
}

