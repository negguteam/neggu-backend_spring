package com.neggu.neggu.dto.user

import io.swagger.v3.oas.annotations.media.Schema

data class DuplicateNicknameResponse(
    @field:Schema(description = "닉네임 중복 여부", example = "false")
    val isDuplicate: Boolean,
)
