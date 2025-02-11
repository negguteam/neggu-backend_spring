package com.neggu.neggu.controller.v1.cloth

import com.neggu.neggu.annotation.AccessTokenRequire
import com.neggu.neggu.config.LoginUser
import com.neggu.neggu.dto.cloth.ClothModifyRequest
import com.neggu.neggu.dto.cloth.ClothRegisterRequest
import com.neggu.neggu.model.cloth.*
import com.neggu.neggu.model.user.User
import com.neggu.neggu.service.cloth.ClothService
import com.neggu.neggu.util.toObjectId
import io.swagger.v3.oas.annotations.Parameter
import org.bson.types.ObjectId
import org.springframework.data.domain.Page
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
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
        @PathVariable
        @Parameter(description = "옷 ID") id: String
    ): Cloth {
        return clothService.getCloth(id.toObjectId())
    }

    @AccessTokenRequire
    @GetMapping("/page")
    override fun getClothes(
        @LoginUser user: User,
        @RequestParam(required = false) category: Category?,
        @RequestParam(required = false) subCategory: SubCategory?,
        @RequestParam(required = false) colorGroup: ColorGroup?,
        @RequestParam(required = false) mood: Mood?,
        @RequestParam size: Int,
        @RequestParam page: Int
    ): Page<Cloth> {
        return clothService.getClothes(user, category, subCategory, colorGroup, mood, size, page)
    }

    @AccessTokenRequire
    @PostMapping(value = [""],
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    override fun registerCloth(
        @LoginUser user: User,
        @RequestParam(required = false) image: MultipartFile,
        @RequestPart clothRegisterRequest: ClothRegisterRequest,
    ): Cloth {
        return clothService.registerCloth(user, image, clothRegisterRequest)
    }


    @AccessTokenRequire
    @PostMapping("/modify")
    override fun modifyCloth(
        @LoginUser user: User,
        @RequestBody clothModifyRequest: ClothModifyRequest
    ): Cloth {
        return clothService.modifyCloth(user, clothModifyRequest)
    }

    @AccessTokenRequire
    @DeleteMapping("/{id}")
    override fun deleteCloth(
        @LoginUser user: User,
        @PathVariable id: String
    ): Cloth {
        return clothService.deleteCloth(user, id.toObjectId())
    }

    @AccessTokenRequire
    @GetMapping("/brands")
    override fun getBrands(
        @RequestParam query: String?
    ): List<ClothBrand> {
        return clothService.getBrands(query)
    }
}