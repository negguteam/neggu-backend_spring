package com.neggu.neggu.controller.v1

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/cloths")
class ClothController {

    @GetMapping("/{id}")
    fun getCloth(
        @PathVariable id: String
    ): String {
        return "id: $id"
    }

    @PostMapping
    fun createCloth(): String {
        return "createCloth"
    }
}