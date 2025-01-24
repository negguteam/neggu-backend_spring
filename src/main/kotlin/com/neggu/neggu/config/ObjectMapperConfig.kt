package com.neggu.neggu.config

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule
import com.neggu.neggu.util.toObjectId
import org.bson.types.ObjectId
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder

@Configuration
class ObjectMapperConfig {

    @Bean
    fun jackson2ObjectMapperBuilder(): Jackson2ObjectMapperBuilder {
        val builder = Jackson2ObjectMapperBuilder()
        val objectIdModule =
            SimpleModule().apply {
                addSerializer(ObjectId::class.java, ObjectIdSerializer())
                addDeserializer(ObjectId::class.java, ObjectIdDeserializer())
            }

        builder.modulesToInstall(
            ParameterNamesModule(),
            Jdk8Module(),
            JavaTimeModule(),
            KotlinModule.Builder().build(),
            objectIdModule,
        )
        builder.featuresToDisable(
            MapperFeature.DEFAULT_VIEW_INCLUSION,
            DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
            SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,
            SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS,
        )
        builder.serializationInclusion(JsonInclude.Include.NON_NULL)

        return builder
    }

    private class ObjectIdSerializer : JsonSerializer<ObjectId>() {
        override fun serialize(
            id: ObjectId,
            generator: JsonGenerator,
            serializers: SerializerProvider,
        ) {
            generator.writeString(id.toHexString())
        }
    }

    private class ObjectIdDeserializer : JsonDeserializer<ObjectId>() {
        override fun deserialize(
            parser: JsonParser,
            context: DeserializationContext,
        ): ObjectId {
            return parser.valueAsString.toObjectId()
        }
    }
}
