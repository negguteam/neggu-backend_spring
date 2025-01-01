package com.neggu.neggu.controller.v1.user

import com.neggu.neggu.dto.user.TokenRequest
import com.neggu.neggu.model.user.User
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity


@Tag(name = "01. [유저]")
interface UserApi {

    @Operation(summary = "로그아웃 API")
    @ApiResponse(responseCode = "204", description = "로그아웃 성공")
    fun logout(
        @Schema(hidden = true) user: User,
        tokenRequest: TokenRequest,
    ): ResponseEntity<Unit>

    @Operation(summary = "회원 탈퇴 API 입니다.")
    @ApiResponse(responseCode = "204", description = "회원 탈퇴 성공")
    fun withdraw(
        @Schema(hidden = true) user: User,
    ): ResponseEntity<Unit>

//    @Operation(summary = "마이페이지 API 입니다.")
//    @ApiResponse(responseCode = "200", description = "마이페이지 조회 성공")
//    fun findProfile(
//        @Schema(hidden = true) user: User,
//    ): MyProfileResponse
//    @Operation(summary = "닉네임 수정 API 입니다.")
//    @ApiResponse(responseCode = "204", description = "닉네임 수정 성공")
//    fun updateNickname(
//        @Schema(hidden = true) user: User,
//        updateNickName: MyProfileUpdateRequest.Nickname,
//    ): ResponseEntity<Void>
//
//    @Operation(summary = "알림 설정 수정 API 입니다.")
//    @ApiResponse(responseCode = "204", description = "알람 설정 여부 변경 성공")
//    fun updateAlarmStatus(
//        @Schema(hidden = true) user: User,
//        updateAlarmStatus: MyProfileUpdateRequest.AlarmStatus,
//    ): ResponseEntity<Void>
//
//    @Operation(summary = "알림 시간대 수정 API 입니다.")
//    @ApiResponse(responseCode = "204", description = "알람 사간대 변경 성공")
//    fun updateAlarmTime(
//        @Schema(hidden = true) user: User,
//        updateAlarmTime: MyProfileUpdateRequest.AlarmTime,
//    ): ResponseEntity<Void>
}

