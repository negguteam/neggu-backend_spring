package com.neggu.neggu.dto.fcm

data class FcmMessageRequestDTO(
    val token: String,
    val title: String,
    val body: String,
)
