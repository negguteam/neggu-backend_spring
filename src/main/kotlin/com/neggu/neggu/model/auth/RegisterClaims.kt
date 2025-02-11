package com.neggu.neggu.model.auth

data class RegisterClaims(
    val email: String,
    val profileImage: String?,
    val provider: String,
)