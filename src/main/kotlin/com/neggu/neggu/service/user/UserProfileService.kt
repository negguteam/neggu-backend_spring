package com.neggu.neggu.service.user

import com.neggu.neggu.model.user.User
import com.neggu.neggu.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserProfileService(
    private val userRepository: UserRepository,
) {

//    @Transactional
//    fun updateNickname(
//        user: User,
//        nickname: String,
//    ) {
//        user.updateNickname(nickname)
//        userRepository.save(user)
//    }
//
//    @Transactional
//    fun updateAlarmStatus(
//        user: User,
//        alarmStatus: Boolean,
//    ) {
//        user.updateAlarmStatus(alarmStatus)
//        userRepository.save(user)
//    }

}