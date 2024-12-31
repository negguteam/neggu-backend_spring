package com.neggu.neggu.repository

import com.neggu.neggu.model.user.RefreshToken
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface RefreshTokenRepository: MongoRepository<RefreshToken, ObjectId> {

    fun deleteByUserId(userId: ObjectId?)
}