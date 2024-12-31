package com.neggu.neggu.service.oauth

import com.neggu.neggu.model.user.OidcUser


interface OpenIdTokenResolver {

    fun resolveIdToken(idToken: String): OidcUser
}