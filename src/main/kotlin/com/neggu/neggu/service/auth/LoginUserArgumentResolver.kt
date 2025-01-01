package com.neggu.neggu.service.auth

import com.neggu.neggu.config.LoginUser
import com.neggu.neggu.exception.ErrorType
import com.neggu.neggu.exception.NotFoundException
import com.neggu.neggu.exception.UnAuthorizedException
import com.neggu.neggu.model.auth.UserClaims
import com.neggu.neggu.model.user.User
import com.neggu.neggu.repository.UserRepository
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.context.request.RequestAttributes
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Component
class LoginUserArgumentResolver(
    private val userRepository: UserRepository,
) : HandlerMethodArgumentResolver {

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.hasParameterAnnotation(LoginUser::class.java) &&
                parameter.parameterType === User::class.java
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?,
    ): Any? {
        val value =
            webRequest.getAttribute(ATTRIBUTE_KEY, RequestAttributes.SCOPE_REQUEST)
                ?: throw UnAuthorizedException(ErrorType.UNAUTHORIZED)
        val userClaims: UserClaims = value as UserClaims
        return userRepository.findById(userClaims.id)
            .orElseThrow { NotFoundException(ErrorType.USER_NOT_FOUND) }
    }

    companion object {
        private const val ATTRIBUTE_KEY = "claims"
    }
}