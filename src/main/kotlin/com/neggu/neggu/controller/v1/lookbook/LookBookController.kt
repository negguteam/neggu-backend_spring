package com.neggu.neggu.controller.v1.lookbook

import com.neggu.neggu.model.cloth.Category
import com.neggu.neggu.model.cloth.Cloth
import com.neggu.neggu.model.cloth.ColorGroup
import com.neggu.neggu.model.user.User
import com.neggu.neggu.service.cloth.ClothService
import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.RestController

@RestController
class LookBookController(
    private val clothService: ClothService,
) : LookBookApi {

    override fun getClothes(user: User, filterCategory: Category?, colorGroup: ColorGroup, size: Int, page: Int): Page<Cloth> {
        // TODO
        return Page.empty()
    }

}