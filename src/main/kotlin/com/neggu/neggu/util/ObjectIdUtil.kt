package com.neggu.neggu.util

import com.neggu.neggu.exception.ErrorType
import com.neggu.neggu.exception.ServerException
import org.bson.types.ObjectId

fun String.toObjectId(): ObjectId {
    return try {
        ObjectId(this)
    } catch (e: IllegalArgumentException) {
        throw ServerException(ErrorType.InvalidObjectId)
    }
}