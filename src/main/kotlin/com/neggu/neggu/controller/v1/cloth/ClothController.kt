package com.neggu.neggu.controller.v1.cloth

import com.neggu.neggu.annotation.AccessTokenRequire
import com.neggu.neggu.config.LoginUser
import com.neggu.neggu.dto.base.CommonResponse
import com.neggu.neggu.model.cloth.Cloth
import com.neggu.neggu.model.user.User
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
) : ClothApi {

    @AccessTokenRequire
    @GetMapping("/{id}")
    override fun getCloth(
        @LoginUser user: User,
        @PathVariable @Parameter(description = "옷 ID") id: String
    ): Cloth {
        return clothService.getCloth(ObjectId(id))
    }

    @AccessTokenRequire
    @GetMapping("/all")
    override fun getClothes(
        @LoginUser user: User
    ): List<Cloth> {
        return clothService.getCloths(user)
    }
}