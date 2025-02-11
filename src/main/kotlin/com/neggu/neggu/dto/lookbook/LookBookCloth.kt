package com.neggu.neggu.dto.lookbook

import io.swagger.v3.oas.annotations.media.Schema

data class LookBookCloth(
    @Schema(description = "이미지 URL", example = "https://example.com/image/12345")
    val imageUrl: String,
    @Schema(description = "옷 ID", example = "678a539bc66af10ded74a1fb (옷 ID)")
    val clothId: String,
    val xPercentage: Float,
    val yPercentage: Float,
    @Schema(description = "스케일", example = "0.5")
    val scale: Float,
    @Schema(description = "회전 각도", example = "40")
    val angle: Int,
    val zIndex: Int,
)