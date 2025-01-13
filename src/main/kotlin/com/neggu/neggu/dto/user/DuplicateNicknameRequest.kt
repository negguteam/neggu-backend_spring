package com.neggu.neggu.dto.user

import io.swagger.v3.oas.annotations.media.Schema

data class DuplicateNicknameRequest(
    @field:Schema(description = "닉네임", example = "테스트_닉네임")
    val nickName: String,
)
