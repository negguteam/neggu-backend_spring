package com.neggu.neggu.repository

import com.neggu.neggu.model.cloth.Cloth
import org.bson.types.ObjectId
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository

interface ClothRepository : MongoRepository<Cloth, ObjectId> {

    fun findAllByAccountId(id: ObjectId?, pageable: Pageable): Page<Cloth>
}