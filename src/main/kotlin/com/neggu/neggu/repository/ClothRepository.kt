package com.neggu.neggu.repository

import com.neggu.neggu.model.cloth.Cloth
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface ClothRepository : MongoRepository<Cloth, ObjectId>