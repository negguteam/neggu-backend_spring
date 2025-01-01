package com.neggu.neggu.repository

import com.neggu.neggu.model.user.OauthProvider
import com.neggu.neggu.model.user.User
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository: MongoRepository<User, ObjectId> {

    fun findByEmailAndOauthProvider(email: String, oauthProvider: OauthProvider): User?
}