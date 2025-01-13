package com.neggu.neggu.controller.v1.lookbook

import com.neggu.neggu.model.cloth.Category
import com.neggu.neggu.model.cloth.Cloth
import com.neggu.neggu.model.cloth.ColorGroup
import com.neggu.neggu.model.user.User
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Page

@Tag(name = "04. [룩북]")
interface LookBookApi {

    @Operation(summary = "룩복을 꾸밀 옷 조회")
    fun getClothes(
        @Schema(hidden = true) user: User,
        filterCategory: Category?,
        colorGroup: ColorGroup,
        size: Int,
        page: Int,
    ): Page<Cloth>

}