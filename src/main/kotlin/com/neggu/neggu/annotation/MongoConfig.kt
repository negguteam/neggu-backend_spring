package com.neggu.neggu.annotation

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.config.EnableMongoAuditing
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper
import org.springframework.data.mongodb.core.convert.MappingMongoConverter

@Configuration
@EnableMongoAuditing
class MongoConfig(
    @Value("\${spring.data.mongodb.uri}")
    var mongoUri: String
) {

    @Bean
    fun mongoTemplate(): MongoTemplate {
        return MongoTemplate(SimpleMongoClientDatabaseFactory(mongoUri)).apply { (converter as MappingMongoConverter).setTypeMapper(DefaultMongoTypeMapper(null)) }
    }
}