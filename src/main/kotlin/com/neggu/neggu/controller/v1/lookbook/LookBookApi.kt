package com.neggu.neggu.controller.v1.lookbook

import com.neggu.neggu.dto.lookbook.LookBookCloth
import com.neggu.neggu.model.cloth.Category
import com.neggu.neggu.model.cloth.Cloth
import com.neggu.neggu.model.cloth.ColorGroup
import com.neggu.neggu.model.lookbook.LookBook
import com.neggu.neggu.model.user.User
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Page
import org.springframework.web.multipart.MultipartFile

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

    @Operation(summary = "룩복 저장")
    fun saveLookBook(
        @Schema(hidden = true) user: User,
        image: MultipartFile,
        lookBookClothes: List<LookBookCloth>,
    ): LookBook


    @Operation(summary = "룩북 리스트 조회")
    fun getLookBooks(
        @Schema(hidden = true) user: User,
        size: Int,
        page: Int,
    ): Page<LookBook>

    @Operation(summary = "룩북 삭제")
    fun deleteLookBook(
        @Schema(hidden = true) user: User,
        lookBookId: String,
    ): LookBook

    @Operation(summary = "룩북 상세 조회")
    fun getLookBook(
        @Schema(hidden = true) user: User,
        lookBookId: String,
    ): LookBook

}