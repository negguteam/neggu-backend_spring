package com.neggu.neggu.service.fcm

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.neggu.neggu.config.LoggerConfig.log
import com.neggu.neggu.config.LoggerConfig.nError
import com.neggu.neggu.dto.fcm.FcmMessageRequestDTO
import org.springframework.stereotype.Service

@Service
class FcmService {

    fun sendMessage(fcmMessageRequestDTO: FcmMessageRequestDTO): String {
        val message = generateMessage(fcmMessageRequestDTO)
        return try {
            val response = FirebaseMessaging.getInstance().send(message)
            "Message sent successfully : $response"
        } catch (e: Exception) {
            e.printStackTrace()
            log.nError(e.message.toString())
            "Failed to send message"
        }
    }

    private fun generateMessage(fcmMessageRequestDTO: FcmMessageRequestDTO): Message =
        Message.builder()
            .setToken(fcmMessageRequestDTO.token)
            .putData("title", fcmMessageRequestDTO.title)
            .putData("body", fcmMessageRequestDTO.body)
            .build()
}