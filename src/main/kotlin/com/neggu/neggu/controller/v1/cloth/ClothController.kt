package com.neggu.neggu.controller.v1.cloth

import com.neggu.neggu.annotation.AccessTokenRequire
import com.neggu.neggu.config.LoginUser
import com.neggu.neggu.dto.cloth.ClothRegisterRequest
import com.neggu.neggu.model.cloth.Cloth
import com.neggu.neggu.model.user.User
import com.neggu.neggu.service.ClothService
import io.swagger.v3.oas.annotations.Parameter
import org.bson.types.ObjectId
import org.springframework.data.domain.Page
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

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
    @GetMapping("/page")
    override fun getClothes(
        @LoginUser user: User,
        @RequestParam size: Int,
        @RequestParam page: Int
    ): Page<Cloth> {
        return clothService.getClothPage(user, size, page)
    }

    @AccessTokenRequire
    @PostMapping(value = [""],
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    override fun postCloth(
        @LoginUser user: User,
        @RequestParam image: MultipartFile,
        @RequestParam registerRequest: ClothRegisterRequest
    ): Cloth {
        return clothService.postCloth(user, image, registerRequest)
    }
}