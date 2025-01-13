package com.neggu.neggu.model.cloth

data class RGBColor(
    val red: Int,
    val green: Int,
    val blue: Int,
) {

    companion object {

        fun fromHex(hex: String): RGBColor {
            require(hex.startsWith("#") && hex.length == 7) { "Invalid HEX color format : $hex" }
            val red = hex.substring(1, 3).toInt(16)
            val green = hex.substring(3, 5).toInt(16)
            val blue = hex.substring(5, 7).toInt(16)
            return RGBColor(red, green, blue)
        }
    }
}