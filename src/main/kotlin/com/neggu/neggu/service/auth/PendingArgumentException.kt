package com.neggu.neggu.service.auth

import com.neggu.neggu.annotation.PendingUser
import com.neggu.neggu.model.auth.RegisterClaims
import com.neggu.neggu.service.jwt.JwtProvider
import jakarta.servlet.http.HttpServletRequest
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Component
class PendingUserArgumentResolver(
    private val jwtProvider: JwtProvider,
) : HandlerMethodArgumentResolver {

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.hasParameterAnnotation(PendingUser::class.java) &&
                parameter.parameterType === RegisterClaims::class.java
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?,
    ): Any? {
        val request: HttpServletRequest = webRequest.nativeRequest as HttpServletRequest
        val registerToken: String? = request.getHeader(ATTRIBUTE_KEY) ?: null
        return jwtProvider.resolveRegisterToken(registerToken)
    }

    companion object {
        private const val ATTRIBUTE_KEY = "RegisterToken"
    }
}