package com.neggu.neggu.model.user

import com.neggu.neggu.model.auth.OauthProvider
import com.neggu.neggu.model.base.AuditableEntity
import com.neggu.neggu.model.cloth.Mood
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

@Document("user")
data class User(
    @Id
    @get:JvmName("getUserId")
    val id: ObjectId? = null,
    val email: String,
    val nickname: String,
    val gender: Gender,
    val mood: List<Mood> = emptyList(),
    val age: Int,
    val profileImage: String?,
    val clothes: List<ObjectId> = emptyList(),
    val role: String = UserRole.ROLE_USER.value,
    val status: Status = Status.ACTIVE,
    val isDeleted: Boolean = false,
    val oauthProvider: OauthProvider = OauthProvider.KAKAO
) : AuditableEntity() {

    override fun getId(): ObjectId? = id

    fun getAuthorities(): Collection<GrantedAuthority> {
        return role.split(",").map { SimpleGrantedAuthority(it) }
    }
}
