package com.neggu.neggu.service

import com.neggu.neggu.exception.ErrorType
import com.neggu.neggu.exception.NotFoundException
import com.neggu.neggu.model.cloth.Cloth
import com.neggu.neggu.model.user.User
import com.neggu.neggu.repository.ClothRepository
import org.bson.types.ObjectId
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ClothService(
    private val clothRepository: ClothRepository
) {

    fun getCloths(user: User): List<Cloth> {
        return clothRepository.findAllById(user.clothes)
    }

    fun getCloth(id: ObjectId): Cloth {
        return clothRepository.findByIdOrNull(id) ?: throw NotFoundException(ErrorType.NOT_FOUND_CLOTH)
    }
}