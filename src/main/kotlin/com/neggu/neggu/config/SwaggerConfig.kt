package com.neggu.neggu.config

import com.neggu.neggu.annotation.ApiErrorResponse
import com.neggu.neggu.annotation.ApiErrorResponses
import com.neggu.neggu.exception.ErrorResponse
import com.neggu.neggu.exception.ErrorType
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.Operation
import io.swagger.v3.oas.models.examples.Example
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springdoc.core.customizers.OperationCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import io.swagger.v3.oas.models.media.Content
import io.swagger.v3.oas.models.media.MediaType
import io.swagger.v3.oas.models.responses.ApiResponse
import io.swagger.v3.oas.models.responses.ApiResponses
import kotlin.reflect.KClass


@Configuration
class OpenApiConfig {


    companion object {
        const val AUTHORIZATION = "AccessToken"
    }

    @Bean
    fun springOpenApi(): OpenAPI {
        return OpenAPI()
            .components(swaggerComponent())
            .addSecurityItem(securityItem())
            .info(swaggerInfo())
    }

    @Bean
    fun customize(): OperationCustomizer =
        OperationCustomizer { operation, handlerMethod ->
            handlerMethod.getMethodAnnotation(ApiErrorResponses::class.java)?.let { apiErrorResponses ->
                apiErrorResponses.value.forEach { value ->
                    generateErrorResponse(operation, value.errorType, value.description)
                }
            } ?: handlerMethod.getMethodAnnotation(ApiErrorResponse::class.java)?.let { apiErrorResponse ->
                generateErrorResponse(operation, apiErrorResponse.errorType, apiErrorResponse.description)
            }
            operation
        }

    private fun swaggerComponent(): Components {
        val tokenSchema: SecurityScheme =
            SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .name(AUTHORIZATION)
                .`in`(SecurityScheme.In.HEADER)

        return Components()
            .addSecuritySchemes(AUTHORIZATION, tokenSchema)
    }

    private fun securityItem(): SecurityRequirement {
        return SecurityRequirement()
            .addList(AUTHORIZATION)
    }

    private fun swaggerInfo(): Info {
        return Info()
            .title("\uD83C\uDF40 Neggu \uD83C\uDF40")
            .description("API 명세서입니다")
            .version("v1.0.0")
    }

    private fun generateErrorResponse(
        operation: Operation,
        errorCode: KClass<out ErrorType>,
        description: String,
    ) {
        val responses = operation.responses
        val exampleDto = createExampleDto(errorCode)
        addExampleToApiResponse(responses, description, exampleDto)
    }

    private fun createExampleDto(errorCode: KClass<out ErrorType>): ExampleDto {
        val errorInstance = errorCode.objectInstance ?: errorCode.constructors.first().call()
        val error = ErrorResponse.from(errorInstance)
        val example =
            Example().apply { this.value(error) }
        return ExampleDto(
            name = errorInstance::class.simpleName ?: "Unknown Error Type",
            code = errorInstance.status.value(),
            holder = example,
        )
    }

    private fun addExampleToApiResponse(
        responses: ApiResponses,
        description: String,
        exampleDto: ExampleDto,
    ) {
        val mediaType =
            MediaType().apply {
                addExamples(exampleDto.name, exampleDto.holder)
            }
        val content =
            Content().apply {
                addMediaType("application/json", mediaType)
            }
        val apiResponse =
            ApiResponse().apply {
                this.content = content
                this.description = description
            }
        responses.addApiResponse(exampleDto.code.toString(), apiResponse)
    }

    data class ExampleDto(
        val name: String,
        val code: Int,
        val holder: Example,
    )
}
