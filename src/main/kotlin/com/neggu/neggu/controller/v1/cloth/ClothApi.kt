package com.neggu.neggu.controller.v1.cloth

import com.neggu.neggu.annotation.ApiErrorResponse
import com.neggu.neggu.annotation.ApiErrorResponses
import com.neggu.neggu.exception.ErrorType
import com.neggu.neggu.model.cloth.Cloth
import com.neggu.neggu.model.user.User
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PathVariable

@Tag(name = "03. [옷장]")
interface ClothApi {

    @Operation(summary = "내 옷장 조회 API")
    @ApiResponse(
        responseCode = "200",
        description = "내 옷장 조회 성공",
        content = [
            Content(
                array = ArraySchema(schema = Schema(implementation = Cloth::class))
            )
        ]
    )
    @ApiErrorResponses(
        [
            ApiErrorResponse(ErrorType.NOT_FOUND_CLOTH, "해당 옷이 존재하지 않을 시 에러입니다."),
        ]
    )
    fun getClothes(
        @Schema(hidden = true) user: User,
    ) : List<Cloth>

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
        @PathVariable @Parameter(description = "옷 ID") id: String
    ): Cloth

}