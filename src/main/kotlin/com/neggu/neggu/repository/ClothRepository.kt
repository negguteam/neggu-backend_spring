package com.neggu.neggu.repository

import com.neggu.neggu.model.cloth.*
import org.bson.types.ObjectId
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface ClothRepository : MongoRepository<Cloth, ObjectId>, CustomClothRepository {

    fun findAllByAccountId(id: ObjectId?, pageable: Pageable): Page<Cloth>
}