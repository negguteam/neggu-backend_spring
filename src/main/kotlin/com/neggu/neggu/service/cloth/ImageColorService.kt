package com.neggu.neggu.service.cloth

import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import javax.imageio.ImageIO

@Service
class ImageColorService {

    fun pickColor(multipartFile: MultipartFile): String {
        // MultipartFile -> BufferedImage 변환
        val inputStream = ByteArrayInputStream(multipartFile.bytes)
        val image: BufferedImage = ImageIO.read(inputStream)
            ?: throw IllegalArgumentException("Invalid image file")

        // 각 픽셀의 색상 값을 저장할 맵
        val colorCountMap = mutableMapOf<Color, Int>()

        // 이미지의 각 픽셀의 색상 값을 맵에 저장
        for (x in 0 until image.width) {
            for (y in 0 until image.height) {
                val color = Color(image.getRGB(x, y))
                colorCountMap[color] = colorCountMap.getOrDefault(color, 0) + 1
            }
        }

        // 가장 많은 색상 찾기
        val mostFrequentColor = colorCountMap.maxByOrNull { it.value }?.key
            ?: throw IllegalArgumentException("No colors found in the image")

        // RGB 값을 HEX로 변환
        val hexColor =
            String.format("#%02x%02x%02x", mostFrequentColor.red, mostFrequentColor.green, mostFrequentColor.blue)
        return hexColor
    }

}