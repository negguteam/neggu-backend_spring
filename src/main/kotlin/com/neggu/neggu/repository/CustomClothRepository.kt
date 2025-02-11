package com.neggu.neggu.repository

import com.neggu.neggu.model.cloth.*
import org.bson.types.ObjectId
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CustomClothRepository {

    fun findClothesDynamic(
        acountId: ObjectId,
        category: Category?,
        subCategory: SubCategory?,
        colors: List<ClothColor>?,
        moods: Mood?,
        pageable: Pageable
    ): Page<Cloth>
}
