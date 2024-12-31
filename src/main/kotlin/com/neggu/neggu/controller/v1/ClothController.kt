package com.neggu.neggu.controller.v1

import com.neggu.neggu.dto.base.CommonResponse
import com.neggu.neggu.model.cloth.Cloth
import com.neggu.neggu.service.ClothService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import org.bson.types.ObjectId
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/cloth")
class ClothController(
    private val clothService: ClothService
) {

    @GetMapping("/{id}")
    @Operation(summary = "옷 조회")
    fun getCloth(
        @Parameter(description = "아이디", example = "60f3b3b3b3b3b3b3b3b3b3b3")
        @PathVariable id: String
    ): CommonResponse<Cloth> {
        return CommonResponse.onSuccess(clothService.getCloth(ObjectId(id)))
    }

    @GetMapping("/all")
    @Operation(summary = "모든 옷 조회")
    fun getCloths(): CommonResponse<List<Cloth>> {
        return CommonResponse.onSuccess(clothService.getCloths())
    }
}