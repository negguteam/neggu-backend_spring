package com.neggu.neggu.controller.v1.user


import com.neggu.neggu.annotation.PendingUser
import com.neggu.neggu.config.LoginUser
import com.neggu.neggu.dto.user.TokenResponse
import com.neggu.neggu.dto.user.UserRegisterRequest
import com.neggu.neggu.model.oauth.RegisterClaims
import com.neggu.neggu.model.user.OauthProvider
import com.neggu.neggu.model.user.User
import com.neggu.neggu.service.user.UserRegisterService
import com.neggu.neggu.service.user.UserWithdrawService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userRegisterService: UserRegisterService,
    private val userWithdrawService: UserWithdrawService,
) : UserApi {

    @PostMapping("/register")
    override fun register(
        @PendingUser registerClaims: RegisterClaims,
        @RequestBody userRegisterRequest: UserRegisterRequest,
    ): TokenResponse {
        println("registerClaims: $registerClaims -----------")
        return userRegisterService.registerUser(
            registerClaims.nickname,
            OauthProvider.from(registerClaims.provider),
            userRegisterRequest,
        )
    }

    @Secured
    @DeleteMapping("/withdraw")
    override fun withdraw(
        @LoginUser user: User,
    ): ResponseEntity<Void> {
        userWithdrawService.withdraw(user)
        return ResponseEntity.noContent().build()
    }

//    @Secured
//    @GetMapping("/my")
//    override fun findProfile(
//        @LoginUser user: User,
//    ): MyProfileResponse {
//        return userProfileService.findProfile(user)
//    }
//
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