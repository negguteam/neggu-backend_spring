package com.neggu.neggu.controller.v1.user


import com.neggu.neggu.annotation.AccessTokenRequire
import com.neggu.neggu.config.LoginUser
import com.neggu.neggu.dto.user.TokenRequest
import com.neggu.neggu.model.user.User
import com.neggu.neggu.service.user.LogoutService
import com.neggu.neggu.service.user.UserWithdrawService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/user")
class UserController(
    private val logoutService: LogoutService,
    private val userWithdrawService: UserWithdrawService,
) : UserApi {

    @AccessTokenRequire
    @PostMapping("/logout")
    override fun logout(
        @LoginUser user: User,
        @RequestBody tokenRequest: TokenRequest,
    ): ResponseEntity<Unit> {
        logoutService.logout(tokenRequest.refreshToken)
        return ResponseEntity.noContent().build()
    }

    @AccessTokenRequire
    @DeleteMapping("/withdraw")
    override fun withdraw(
        @LoginUser user: User,
    ): ResponseEntity<Unit> {
        userWithdrawService.withdraw(user)
        return ResponseEntity.noContent().build()
    }

    @AccessTokenRequire
    @GetMapping("/profile")
    override fun findProfile(
        @LoginUser user: User,
    ): User {
        return user
    }

//    @Secured
//    @PatchMapping("/my/nickname")
//    override fun updateNickname(
//        @LoginUser user: User,
//        @RequestBody updateNickName: MyProfileUpdateRequest.Nickname,
//    ): ResponseEntity<Void> {
//        userProfileService.updateNickname(user, updateNickName.nickname)
//        return ResponseEntity.noContent().build()
//    }
//
//    @Secured
//    @PatchMapping("/my/alarm")
//    override fun updateAlarmStatus(
//        @LoginUser user: User,
//        @RequestBody updateAlarmStatus: MyProfileUpdateRequest.AlarmStatus,
//    ): ResponseEntity<Void> {
//        userProfileService.updateAlarmStatus(user, updateAlarmStatus.alarmStatus)
//        return ResponseEntity.noContent().build()
//    }
//
//    @Secured
//    @PatchMapping("/my/alarm-time")
//    override fun updateAlarmTime(
//        @LoginUser user: User,
//        @RequestBody updateAlarmTime: MyProfileUpdateRequest.AlarmTime,
//    ): ResponseEntity<Void> {
//        userProfileService.updateAlarmTime(user, AlarmTime.from(updateAlarmTime.alarmTime))
//        return ResponseEntity.noContent().build()
//    }
}