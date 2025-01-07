package com.neggu.neggu.service.user

import com.neggu.neggu.exception.ErrorType
import com.neggu.neggu.exception.NotFoundException
import com.neggu.neggu.model.user.User
import com.neggu.neggu.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserWithdrawService(
    private val userRepository: UserRepository,
) {

    fun withdraw(loginUser: User) {
        val user: User =
            userRepository.findByIdOrNull(loginUser.id) ?: throw NotFoundException(ErrorType.UserNotFound)
        userRepository.delete(user)
    }
}