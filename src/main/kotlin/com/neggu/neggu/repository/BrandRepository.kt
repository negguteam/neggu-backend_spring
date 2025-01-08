package com.neggu.neggu.repository

import com.neggu.neggu.model.cloth.ClothBrand
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface BrandRepository : MongoRepository<ClothBrand, ObjectId>