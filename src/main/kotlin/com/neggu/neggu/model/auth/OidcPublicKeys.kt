package com.neggu.neggu.model.auth

data class OidcPublicKeys(
    val keys: List<OidcPublicKey>,
) {

    fun getMatchesKey(
        kid: String,
        alg: String,
    ): OidcPublicKey {
        return keys.firstOrNull { it.kid == kid && it.alg == alg }
            ?: throw IllegalArgumentException("No matching key found")
    }
}