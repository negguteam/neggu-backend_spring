package com.neggu.neggu.model.cloth

import io.swagger.v3.oas.annotations.media.Schema
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("cloth")
@Schema(description = "Clothing data model")
data class Cloth(
    @Id
    @Schema(description = "Primary key of the clothing item", example = "@ObjectId('60b3b3b3d4c1f1b3b3b3d4c1')")
    val id: ObjectId? = null,
    @Schema(description = "Default name generated from (brand + color + subCategory)", example = "아디다스 회색 후드")
    val name: String,
    @Schema(description = "Owner account identifier", example = "user123")
    val account: String,
    @Schema(description = "Link to purchase the clothing item", example = "https://example.com/item/12345")
    val link: String?,
    @Schema(description = "URL of the clothing image", example = "https://example.com/images/12345.jpg")
    val imageUrl: String?,
    @Schema(description = "Main category of the clothing", example = "상의")
    val category: String,
    @Schema(description = "Sub-category of the clothing", example = "Hoodie")
    val subCategory: String,
    @Schema(description = "Mood tags associated with the clothing", example = "CASUAL, STREET")
    val mood: List<String>,
    @Schema(description = "Brand of the clothing", example = "Adidas")
    val brand: String?,
    @Schema(description = "SKU or model number of the clothing", example = "AD12345")
    val sku: String?,
    @Schema(description = "Price range of the clothing", example = "10~20만원")
    val priceRange: String?,
    @Schema(description = "Additional notes or memo", example = "Limited edition item")
    val memo: String?,
    @Schema(description = "Purchase status of the clothing", example = "true")
    val isPurchase: Boolean = true,
    @Schema(description = "Color code of the clothing", example = "#FFFFFF")
    val colorCode: String
)