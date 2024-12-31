package com.neggu.neggu.service

import com.neggu.neggu.model.Cloth
import com.neggu.neggu.repository.ClothRepository
import org.bson.types.ObjectId
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ClothService(
    private val clothRepository: ClothRepository
) {

    fun getCloths(): List<Cloth> = clothRepository.findAll()
    fun getCloth(id: ObjectId): Cloth? {
        return clothRepository.findByIdOrNull(id)
    }
}