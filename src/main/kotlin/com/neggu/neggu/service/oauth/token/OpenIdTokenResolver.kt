package com.neggu.neggu.service.oauth.token

import com.neggu.neggu.model.user.OidcUser


interface OpenIdTokenResolver {

    fun resolveIdToken(idToken: String): OidcUser
}