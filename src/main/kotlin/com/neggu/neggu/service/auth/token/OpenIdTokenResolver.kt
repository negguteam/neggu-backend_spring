package com.neggu.neggu.service.auth.token

import com.neggu.neggu.model.auth.OidcUser


interface OpenIdTokenResolver {

    fun resolveIdToken(idToken: String): OidcUser
}