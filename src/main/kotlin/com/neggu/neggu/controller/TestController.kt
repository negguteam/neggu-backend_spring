package com.neggu.neggu.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/")
@Tag(name = "Test Controller")
class TestController {

    @GetMapping
    @Operation(summary = "98. Test API")
    fun test(): String {
        return "Test Controller Running"
    }
}