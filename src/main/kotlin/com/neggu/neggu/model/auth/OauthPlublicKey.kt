package com.neggu.neggu.model.auth

data class OidcPublicKey(
    val kty: String,
    val kid: String,
    val alg: String,
    val n: String,
    val e: String,
)