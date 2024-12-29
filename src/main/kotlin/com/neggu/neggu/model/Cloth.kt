//package com.neggu.neggu.model
//
//import io.swagger.v3.oas.annotations.media.Schema
//import org.springframework.data.mongodb.core.mapping.Document
//
//@Schema(description = "Clothing data model")
//@Document("cloth")
//data class Cloth(
//    @Schema(description = "Default name generated from brand, color, and subCategory", example = "Adidas Gray Hoodie")
//    val name: String,
//
//    @Schema(description = "Primary key of the clothing item", example = "12345")
//    val id: Long,
//
//    @Schema(description = "Owner account identifier", example = "user123")
//    val account: String,
//
//    @Schema(description = "Link to purchase the clothing item", example = "https://example.com/item/12345")
//    val link: String?,
//
//    @Schema(description = "URL of the clothing image", example = "https://example.com/images/12345.jpg")
//    val imageUrl: String?,
//
//    @Schema(description = "Main category of the clothing", example = "Outerwear")
//    val category: String,
//
//    @Schema(description = "Sub-category of the clothing", example = "Hoodie")
//    val subCategory: String?,
//
//    @Schema(description = "Mood tags associated with the clothing", example = "Casual, Sporty")
//    val mood: List<String>?,
//
//    @Schema(description = "Brand of the clothing", example = "Adidas")
//    val brand: String,
//
//    @Schema(description = "SKU or model number of the clothing", example = "AD12345")
//    val sku: String?,
//
//    @Schema(description = "Price range of the clothing", example = "10~20만원")
//    val priceRange: String?,
//
//    @Schema(description = "Additional notes or memo", example = "Limited edition item")
//    val memo: String?,
//
//    @Schema(description = "Purchase status of the clothing", example = "true")
//    val isPurchase: Boolean = true,
//
//    @Schema(description = "Color code of the clothing", example = "#FFFFFF")
//    val colorCode: String?
//)
