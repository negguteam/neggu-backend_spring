package com.neggu.neggu.model.user

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("refresh_token")
data class RefreshToken(
    @Id
    val userId: ObjectId,
    val token: String,
    val expiration: Long,
) {

    companion object {
        fun create(
            userId: ObjectId,
            token: String,
            expiration: Long,
        ): RefreshToken {
            return RefreshToken(userId, token, expiration)
        }
    }
}