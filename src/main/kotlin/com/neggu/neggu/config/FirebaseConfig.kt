package com.neggu.neggu.config

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.neggu.neggu.config.properties.FirebaseProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class FirebaseConfig(
    private val firebaseProperties: FirebaseProperties,
) {

    @Bean
    fun firebaseApp(): FirebaseApp {
        println(firebaseProperties.credential)
        val options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(firebaseProperties.credential.byteInputStream()))
            .build()
        return FirebaseApp.initializeApp(options)
    }
}