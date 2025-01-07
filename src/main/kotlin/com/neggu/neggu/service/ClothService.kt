package com.neggu.neggu.service

import com.neggu.neggu.exception.ErrorType
import com.neggu.neggu.exception.NotFoundException
import com.neggu.neggu.model.cloth.Cloth
import com.neggu.neggu.model.user.User
import com.neggu.neggu.repository.ClothRepository
import org.bson.types.ObjectId
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ClothService(
    private val clothRepository: ClothRepository
) {

    fun getClothPage(user: User, size:Int, page: Int, sortProperty : String = "createdAt"): Page<Cloth> {
        val pageable = PageRequest.of(page, size, Sort.by(sortProperty).descending())
        return clothRepository.findAllByAccountId(user.id, pageable)
    }

    fun getCloth(id: ObjectId): Cloth {
        return clothRepository.findByIdOrNull(id) ?: throw NotFoundException(ErrorType.NotFoundCloth)
    }
}