package com.neggu.neggu.controller.v1.cloth

import com.neggu.neggu.annotation.ApiErrorResponse
import com.neggu.neggu.annotation.ApiErrorResponses
import com.neggu.neggu.dto.cloth.ClothRegisterRequest
import com.neggu.neggu.exception.ErrorType
import com.neggu.neggu.model.cloth.Cloth
import com.neggu.neggu.model.cloth.ClothBrand
import com.neggu.neggu.model.user.User
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Page
import org.springframework.web.multipart.MultipartFile

@Tag(name = "03. [옷장(의상)]")
interface ClothApi {

    @Operation(summary = "내 옷장 조회 API")
    @ApiErrorResponses(
        [
            ApiErrorResponse(ErrorType.NotFoundCloth::class, "해당 옷이 존재하지 않을 시 에러입니다."),
        ]
    )
    fun getClothes(
        @Schema(hidden = true) user: User,
        size: Int,
        page: Int,
    ): Page<Cloth>

    @Operation(summary = "옷 조회 API")
    @ApiResponse(
        responseCode = "200",
        description = "옷 조회 성공",
        content = [
            Content(
                schema = Schema(implementation = Cloth::class)
            )
        ]
    )
    fun getCloth(
        @Schema(hidden = true) user: User,
        @Parameter(description = "옷 ID") id: String,
    ): Cloth

    @Operation(summary = "옷 등록 API")
    @ApiResponse(
        responseCode = "200",
        description = "옷 저장 성공",
        content = [
            Content(
                schema = Schema(implementation = Cloth::class)
            )
        ]
    )
    fun postCloth(
        @Schema(hidden = true) user: User,
        image: MultipartFile,
        clothRegisterRequest: ClothRegisterRequest
    ): Cloth

    @Operation(summary = "옷 삭제 API")
    @ApiResponse(
        responseCode = "200",
        description = "옷 삭제 성공"
    )
    fun deleteCloth(
        @Schema(hidden = true) user: User,
        @Parameter(description = "옷 ID") id: String
    ): Cloth

    @Operation(summary = "브랜드 목록 조회 API")
    @ApiResponse(
        responseCode = "200",
        description = "브랜드 목록 조회 성공"
    )
    fun getBrands(): List<ClothBrand>
}
