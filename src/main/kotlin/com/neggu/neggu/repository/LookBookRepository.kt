package com.neggu.neggu.repository

import com.neggu.neggu.model.lookbook.LookBook
import org.bson.types.ObjectId
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository

interface LookBookRepository : MongoRepository<LookBook, ObjectId> {

    fun findByAccountId(accountId: ObjectId, pageable: Pageable): Page<LookBook>
    fun deleteAllByAccountId(accountId: ObjectId)
}
