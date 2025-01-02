package com.neggu.neggu.model.lookbook

import com.mongodb.client.model.geojson.Position
import com.neggu.neggu.model.base.AuditableEntity
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("lookbook")
data class LookBook(
    @Id
    @get:JvmName("getLookBookId")
    val id: ObjectId? = null,
    val accountId: String,
    val clothes: List<String>,
//    val imageUrl: String, // TODO 포지션 및 imageURL문의
//    val position: String?
) : AuditableEntity() {

    override fun getId(): ObjectId? = id
}