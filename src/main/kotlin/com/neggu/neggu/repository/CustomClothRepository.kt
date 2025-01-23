package com.neggu.neggu.repository

import com.neggu.neggu.model.cloth.Category
import com.neggu.neggu.model.cloth.Cloth
import com.neggu.neggu.model.cloth.ClothColor
import com.neggu.neggu.model.cloth.Mood
import org.bson.types.ObjectId
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CustomClothRepository {

    fun findClothesDynamic(
        acountId: ObjectId,
        category: Category?,
        colors: List<ClothColor>?,
        moods: Mood?,
        pageable: Pageable
    ): Page<Cloth>
}
