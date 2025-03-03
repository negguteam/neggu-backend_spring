package com.neggu.neggu.model.auth

import org.apache.coyote.BadRequestException
import java.lang.Exception

enum class OauthProvider {
    KAKAO,
    APPLE,
    GOOGLE,
    ;

    companion object {
        fun from(provider: String): OauthProvider {
            return try {
                valueOf(provider.uppercase())
            } catch (e: Exception) {
                throw BadRequestException("지원하지 않는 OauthProvider 입니다.")
            }
        }
    }
}