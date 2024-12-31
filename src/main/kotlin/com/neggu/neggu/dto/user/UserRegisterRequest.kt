package com.neggu.neggu.dto.user

import com.neggu.neggu.model.cloth.Mood
import com.neggu.neggu.model.user.*
import io.swagger.v3.oas.annotations.media.Schema

@Schema(
    name = "User Register Request",
    description = "사용자 등록 요청",
)
data class UserRegisterRequest(
    @field:Schema(description = "닉네임", example = "닉네임")
    val nickname: String,
    @field:Schema(description = "성별", example = "MALE")
    val gender: Gender,
    @field:Schema(description = "분위기", example = "[\"FEMININE\", \"CASUAL\"]")
    val mood: List<Mood> = emptyList(),
    @field:Schema(description = "나이", example = "20")
    val age: Int,
)