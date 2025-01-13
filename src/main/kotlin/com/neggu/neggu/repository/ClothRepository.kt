package com.neggu.neggu.repository

import com.neggu.neggu.model.cloth.Category
import com.neggu.neggu.model.cloth.Cloth
import com.neggu.neggu.model.cloth.ClothColor
import com.neggu.neggu.model.cloth.ColorGroup
import org.bson.types.ObjectId
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository

interface ClothRepository : MongoRepository<Cloth, ObjectId> {

    fun findAllByAccountId(id: ObjectId?, pageable: Pageable): Page<Cloth>

    fun findAllByAccountIdAndCategoryAndColorIn(id: ObjectId?, category: Category?, color: List<ClothColor>, pageable: Pageable): Page<Cloth>

    fun findAllByAccountIdAndColorIn(id: ObjectId?, colors: List<ClothColor>, pageable: PageRequest): Page<Cloth>
}