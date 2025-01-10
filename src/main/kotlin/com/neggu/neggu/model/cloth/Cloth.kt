package com.neggu.neggu.model.cloth

import com.neggu.neggu.model.base.AuditableEntity
import io.swagger.v3.oas.annotations.media.Schema
import org.bson.types.ObjectId
import org.springframework.context.annotation.Description
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("cloth")
@Schema(description = "Clothing data model")
data class Cloth(
    @Id
    @Schema(description = "Primary key of the clothing item", example = "60b3b3b3d4c1f1b3b3b3d4c1")
    @get:JvmName("getClothId")
    override var id: ObjectId? = null,
    @Schema(description = "Default name generated from (brand + color + subCategory)", example = "아디다스 회색 후드")
    val name: String,
    @Schema(description = "Owner account identifier", example = "user123")
    val accountId: ObjectId,
    @Schema(description = "URL of the clothing image", example = "https://example.com/images/12345.jpg")
    val imageUrl: String?,
    @Schema(description = "Main category of the clothing", example = "TOP")
    val category: Category,
    @Schema(description = "Sub-category of the clothing", example = "Hoodie")
    val subCategory: SubCategory,
    @Schema(description = "Mood tags associated with the clothing", example = "CASUAL, STREET")
    val mood: List<Mood>,
    @Schema(description = "Brand of the clothing", example = "Adidas")
    val brand: String?,
    @Schema(description = "Price range of the clothing", example = "UNDER_3K")
    val priceRange: PriceRange,
    @Schema(description = "Additional notes or memo", example = "Limited edition item")
    val memo: String = "",
    @Schema(description = "Purchase status of the clothing", example = "true")
    val isPurchase: Boolean = false,
    @Schema(description = "Link to purchase the clothing item", example = "https://example.com/item/12345")
    val link: String?,
    @Schema(description = "Color code of the clothing", example = "#FFFFFF")
    val colorCode: String // 의상 저장시 추출
) : AuditableEntity()