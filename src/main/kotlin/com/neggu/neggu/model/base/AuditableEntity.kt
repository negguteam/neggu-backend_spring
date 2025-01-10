package com.neggu.neggu.model.base

import org.bson.types.ObjectId
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.domain.Persistable
import java.time.LocalDateTime

abstract class AuditableEntity(
    @get:JvmName("getAuditableEntityId")
    open var id: ObjectId? = null,
    @CreatedDate
    var createdAt: LocalDateTime? = null,
    @LastModifiedDate
    var modifiedAt: LocalDateTime? = null,
) : Persistable<ObjectId> {

    override fun getId(): ObjectId? = id
    override fun isNew(): Boolean = id == null
}