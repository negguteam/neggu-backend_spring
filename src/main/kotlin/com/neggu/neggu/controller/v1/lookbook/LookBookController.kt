package com.neggu.neggu.controller.v1.lookbook

import com.neggu.neggu.annotation.AccessTokenRequire
import com.neggu.neggu.config.LoginUser
import com.neggu.neggu.dto.lookbook.LookBookCloth
import com.neggu.neggu.model.cloth.Category
import com.neggu.neggu.model.cloth.Cloth
import com.neggu.neggu.model.cloth.ColorGroup
import com.neggu.neggu.model.lookbook.LookBook
import com.neggu.neggu.model.user.User
import com.neggu.neggu.service.cloth.ClothService
import com.neggu.neggu.service.lookbook.LookBookService
import org.springframework.data.domain.Page
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/v1/lookbook")
class LookBookController(
    private val clothService: ClothService,
    private val lookBookService: LookBookService
) : LookBookApi {

    @AccessTokenRequire
    @GetMapping("/cloth/page")
    override fun getClothes(
        @LoginUser user: User,
        filterCategory: Category?,
        colorGroup: ColorGroup,
        size: Int,
        page: Int,
    ): Page<Cloth> {
        return clothService.getClothes(user, filterCategory, colorGroup, null, size, page)
    }

    @AccessTokenRequire
    @PostMapping(
        value = [""],
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    override fun saveLookBook(
        @LoginUser user: User,
        image: MultipartFile,
        lookBookClothes: List<LookBookCloth>
    ): LookBook {
        return lookBookService.registerLookBook(user, image, lookBookClothes)
    }

    @AccessTokenRequire
    @GetMapping("/page")
    override fun getLookBooks(
        @LoginUser user: User, size: Int, page: Int
    ): Page<LookBook> {
        return lookBookService.getLookBooks(user, size, page)
    }

    @AccessTokenRequire
    @DeleteMapping("/{lookBookId}")
    override fun deleteLookBook(
        @LoginUser user: User,
        @PathVariable lookBookId: String
    ): LookBook {
        return lookBookService.deleteLookBook(user, lookBookId)
    }

    @AccessTokenRequire
    @GetMapping("/{lookBookId}")
    override fun getLookBook(
        @LoginUser user: User, @PathVariable lookBookId: String
    ): LookBook {
        return lookBookService.getLookBook(user, lookBookId)
    }
}