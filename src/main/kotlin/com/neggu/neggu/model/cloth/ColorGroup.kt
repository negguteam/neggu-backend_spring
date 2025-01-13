package com.neggu.neggu.model.cloth

enum class ColorGroup {
    WHITE, GRAY, BLACK, RED, PINK, ORANGE, BROWN, YELLOW, GREEN, BLUE, PURPLE, ALL, NONE;

    fun getColors(): List<ClothColor> {
        return when (this) {
            WHITE -> listOf(ClothColor.WHITE)
            GRAY -> listOf(ClothColor.LIGHT_GRAY, ClothColor.GRAY, ClothColor.DARK_GRAY)
            BLACK -> listOf(ClothColor.BLACK)
            RED -> listOf(ClothColor.DEEP_RED, ClothColor.RED, ClothColor.BURGUNDY)
            PINK -> listOf(ClothColor.PINK, ClothColor.LIGHT_PINK, ClothColor.PALE_PINK)
            ORANGE -> listOf(ClothColor.ORANGE)
            BROWN -> listOf(ClothColor.IVORY, ClothColor.LIGHT_YELLOW, ClothColor.BROWN, ClothColor.KHAKI, ClothColor.KHAKI_BEIGE, ClothColor.CAMEL, ClothColor.BEIGE)
            YELLOW -> listOf(ClothColor.LIGHT_YELLOW, ClothColor.YELLOW)
            GREEN -> listOf(ClothColor.LIGHT_GREEN, ClothColor.MINT, ClothColor.GREEN, ClothColor.OLIVE_GREEN, ClothColor.KHAKI, ClothColor.DARK_GREEN)
            BLUE -> listOf(ClothColor.SKY_BLUE, ClothColor.BLUE, ClothColor.NAVY)
            PURPLE -> listOf(ClothColor.LAVENDER, ClothColor.PURPLE)
            ALL -> ClothColor.entries
            NONE -> listOf(ClothColor.OTHERS)
        }
    }
}
