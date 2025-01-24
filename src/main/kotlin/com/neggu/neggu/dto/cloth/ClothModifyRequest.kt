package com.neggu.neggu.dto.cloth

import com.neggu.neggu.model.cloth.*
import com.neggu.neggu.util.toObjectId
import io.swagger.v3.oas.annotations.media.Schema
import org.bson.types.ObjectId

@Schema(description = "옷 수정 요청",)
data class ClothModifyRequest(
    @Schema(description = "옷 ID", example = "678a539bc66af10ded74a1fb (옷 ID)")
    val id: String,
    @Schema(description = "Account ID of the user", example = "678a539bc66af10ded74a1fb (유저 ID)")
    val accountId: String,
    @Schema(description = "Image URL of the clothing", example = "https://example.com/image/12345")
    val imageUrl: String,
    @Schema(description = "Color code of the clothing", example = "#FFFFFF")
    val colorCode: String,
    @Schema(description = "Name of the clothing", example = "아디다스 회색 후드")
    val name: String,
    @Schema(description = "Main category of the clothing", example = "TOP")
    val category: Category,
    @Schema(description = "Sub-category of the clothing", example = "SHIRT_BLOUSE")
    val subCategory: SubCategory,
    @Schema(description = "Mood tags associated with the clothing", example = "[\"FEMININE\", \"CASUAL\"]")
    val mood: List<Mood>,
    @Schema(description = "Brand of the clothing", example = "Adidas (nullable)")
    val brand: String?,
    @Schema(description = "Price range of the clothing", example = "UNDER_3K")
    val priceRange: PriceRange,
    @Schema(description = "Additional notes or memo", example = "Limited edition item (글자수 제한 X)")
    val memo: String = "",
    @Schema(description = "Purchase status of the clothing", example = "true")
    val isPurchase: Boolean = false,
    @Schema(description = "Link to purchase the clothing item", example = "https://example.com/item/12345 (nullable)")
    val link: String?,
) {

    fun toCloth(clothColor: ClothColor): Cloth {
        return Cloth(
            id = id.toObjectId(),
            accountId = accountId.toObjectId(),
            imageUrl = imageUrl,
            category = category,
            subCategory = subCategory,
            mood = mood,
            brand = brand,
            priceRange = priceRange,
            memo = memo,
            isPurchase = isPurchase,
            link = link,
            name = name,
            colorCode = colorCode,
            color = clothColor,
        )
    }
}
