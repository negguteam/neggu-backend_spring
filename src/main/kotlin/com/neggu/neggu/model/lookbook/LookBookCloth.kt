package com.neggu.neggu.model.lookbook

import io.swagger.v3.oas.annotations.media.Schema

data class LookBookCloth(
    @Schema(name = "imageUrl", example = "https://example.com/image/12345")
    val imageUrl: String,
    @Schema(name = "id", example = "678a539bc66af10ded74a1fb (옷 ID)")
    val id: String,
    @Schema(name = "xRatio", example = "10.5")
    val xRatio: Float,
    @Schema(name = "yRatio", example = "10.5")
    val yRatio: Float,
    @Schema(name = "scale", example = "0.5")
    val scale: Float,
    @Schema(name = "angle", example = "40")
    val angle: Int,
    val zIndex: Int,
)
