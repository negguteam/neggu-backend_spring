package com.neggu.neggu.service.cloth

import com.neggu.neggu.model.cloth.ClothColor
import com.neggu.neggu.model.cloth.RGBColor
import org.springframework.stereotype.Service
import kotlin.math.pow
import kotlin.math.sqrt

@Service
class ColorFinder {

    fun findColor(hex: String): ClothColor {
        val inputRgb = RGBColor.fromHex(hex)
        return ClothColor.entries.toTypedArray().minBy { color ->
            val colorHex = color.hex ?: return@minBy Double.MAX_VALUE
            calculateEuclideanDistance(inputRgb, RGBColor.fromHex(colorHex))
        }
    }

    private fun calculateEuclideanDistance(rgb1: RGBColor, rgb2: RGBColor): Double {
        return sqrt(
            (rgb1.red - rgb2.red).toDouble().pow(2) +
                    (rgb1.green - rgb2.green).toDouble().pow(2) +
                    (rgb1.blue - rgb2.blue).toDouble().pow(2)
        )
    }
}