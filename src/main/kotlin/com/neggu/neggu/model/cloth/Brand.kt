package com.neggu.neggu.model.cloth

import com.neggu.neggu.model.user.Gender
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("brand")
data class ClothBrand(
    @Id
    val id: ObjectId? = null,
    val kr: String,
    val en: String,
    val genders: List<Gender>? = null,
    val moods: List<Mood>? = null,
)
