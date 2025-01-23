package com.neggu.neggu.repository

import com.neggu.neggu.model.cloth.ClothBrand
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface BrandRepository : MongoRepository<ClothBrand, ObjectId> {

    @Query("{\$or : [{'kr' : {\$regex : ?0, \$options: 'i'}}, {'en' : {\$regex : ?0, \$options: 'i'}}]}")
    fun findByNameContaining(name: String): List<ClothBrand>
}