package com.neggu.neggu.service.user

import com.neggu.neggu.exception.ErrorType
import com.neggu.neggu.exception.ServerException
import com.neggu.neggu.model.user.User
import com.neggu.neggu.repository.ClothRepository
import com.neggu.neggu.repository.LookBookRepository
import com.neggu.neggu.repository.UserRepository
import com.neggu.neggu.service.aws.S3Service
import org.bson.types.ObjectId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserWithdrawService(
    private val userRepository: UserRepository,
    private val clothRepository: ClothRepository,
    private val lookBookRepository: LookBookRepository,
    private val s3Service: S3Service,
) {

    fun withdraw(user: User) {
        val userId = user.id ?: throw ServerException(ErrorType.UserNotFound)

        deleteCloth(user, userId)
        deleteLookbook(user)
        deleteUser(userId)
    }

    private fun deleteLookbook(user: User) {
        lookBookRepository.findAllById(user.lookBooks).forEach { lookBook ->
            s3Service.deleteFile(lookBook.imageUrl)
        }
    }

    private fun deleteCloth(user: User, userId: ObjectId) {
        clothRepository.findAllById(user.clothes).forEach { cloth ->
            s3Service.deleteFile(cloth.imageUrl)
        }
        clothRepository.deleteById(userId)
    }

    private fun deleteUser(userId: ObjectId) {
        userRepository.deleteById(userId)
    }
}