package com.neggu.neggu.model.oauth

import org.bson.types.ObjectId

data class UserClaims(
    val id: ObjectId,
    val email: String,
)