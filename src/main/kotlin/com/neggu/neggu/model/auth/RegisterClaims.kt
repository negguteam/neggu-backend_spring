package com.neggu.neggu.model.auth

data class RegisterClaims(
    val email: String,
    val provider: String,
)