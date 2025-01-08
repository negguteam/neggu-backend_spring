package com.neggu.neggu.dto.cloth

import com.neggu.neggu.model.cloth.Category
import com.neggu.neggu.model.cloth.Mood
import com.neggu.neggu.model.cloth.PriceRange
import com.neggu.neggu.model.cloth.SubCategory
import io.swagger.v3.oas.annotations.media.Schema

@Schema(
    name = "ClothRegisterRequest",
    description = "옷 저장 성공 시",
)
data class ClothRegisterRequest(
    @Schema(description = "Main category of the clothing", example = "TOP")
    val category: Category,
    @Schema(description = "Sub-category of the clothing", example = "SHIRT_BLOUSE")
    val subCategory: SubCategory,
    @Schema(description = "Mood tags associated with the clothing", example = "CASUAL, STREET")
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
)