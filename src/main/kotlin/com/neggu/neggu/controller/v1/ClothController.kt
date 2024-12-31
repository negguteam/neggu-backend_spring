package com.neggu.neggu.controller.v1

import com.neggu.neggu.dto.response.CommonResponse
import com.neggu.neggu.model.Cloth
import com.neggu.neggu.service.ClothService
import org.bson.types.ObjectId
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/cloth")
class ClothController(
    private val clothService: ClothService
) {

    @GetMapping("/{id}")
    fun getCloth(
        @PathVariable id: String
    ): CommonResponse<Cloth> {
        return CommonResponse.onSuccess(clothService.getCloth(ObjectId(id)))
    }

    @GetMapping("/all")
    fun getCloths(): CommonResponse<List<Cloth>> {
        return CommonResponse.onSuccess(clothService.getCloths())
    }
}