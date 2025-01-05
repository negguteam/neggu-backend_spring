package com.neggu.neggu.config

import com.neggu.neggu.exception.BaseException
import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.logging.LogLevel
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.Executors


@Component
@Order(Ordered.LOWEST_PRECEDENCE)
object LoggerConfig {

    private lateinit var slackProperties: SlackProperties
    private lateinit var slackHeaders: HttpHeaders

    private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    private val restTemplate = RestTemplate()
    private val MAX_LOG_SIZE = 1_000

    private val logDispatcher = Executors.newFixedThreadPool(2).asCoroutineDispatcher()
    private val slackScope = CoroutineScope(logDispatcher)

    @Autowired
    fun init(properties: SlackProperties) {
        slackProperties = properties
        slackHeaders = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_JSON
            set("Authorization", "Bearer ${slackProperties.token}")
        }
    }

    private fun getTimeBlock() = mapOf(
        "type" to "context",
        "elements" to listOf(
            mapOf(
                "type" to "mrkdwn",
                "text" to "Sent at: ${LocalDateTime.now().format(dateFormatter)}"
            )
        )
    )

    private fun getHeaderBlock(logLevel: LogLevel) = when (logLevel) {
        LogLevel.DEBUG -> mapOf(
            "type" to "header",
            "text" to mapOf(
                "type" to "plain_text",
                "text" to "🔍 Debug Notification",
                "emoji" to true
            )
        )

        LogLevel.INFO -> mapOf(
            "type" to "header",
            "text" to mapOf(
                "type" to "plain_text",
                "text" to "📢 Info Notification",
                "emoji" to true
            )
        )

        LogLevel.WARN -> mapOf(
            "type" to "header",
            "text" to mapOf(
                "type" to "plain_text",
                "text" to "⚠️ Warning Notification",
                "emoji" to true
            )
        )

        LogLevel.ERROR -> mapOf(
            "type" to "header",
            "text" to mapOf(
                "type" to "plain_text",
                "text" to "🔥 Error Notification",
                "emoji" to true
            )
        )

        else -> null
    }

    private fun sendSlackMessage(exception: BaseException) = slackScope.launch {
        try {
            val headerBlock = getHeaderBlock(exception.errorType.logLevel)
            val message = exception.errorType.message
            val stackTrace = exception.stackTraceToString().take(MAX_LOG_SIZE)
            val timeBlock = getTimeBlock()
            val payload = mapOf(
                "channel" to slackProperties.postChatChannel,
                "blocks" to listOfNotNull(
                    headerBlock,
                    mapOf(
                        "type" to "section",
                        "text" to mapOf(
                            "type" to "mrkdwn",
                            "text" to "*Message:*\n${message}"
                        )
                    ),
                    mapOf(
                        "type" to "section",
                        "text" to mapOf(
                            "type" to "mrkdwn",
                            "text" to "*StackTrace:*\n```$stackTrace```"
                        )
                    ),
                    timeBlock
                )
            )
            val entity = HttpEntity(payload, slackHeaders)
            restTemplate.exchange(slackProperties.postChatUrl, HttpMethod.POST, entity, String::class.java)
        } catch (e: Exception) {
            log.nError("Failed to send slack message. ${e.message}")
        }
    }

    private fun sendSlackMessage(logLevel: LogLevel, message: String) = slackScope.launch {
        try {
            val headerBlock = getHeaderBlock(logLevel)
            val timeBlock = getTimeBlock()
            val payload = mapOf(
                "channel" to slackProperties.postChatChannel,
                "blocks" to listOfNotNull(
                    headerBlock,
                    mapOf(
                        "type" to "section",
                        "text" to mapOf(
                            "type" to "mrkdwn",
                            "text" to "*Message:*\n${message}"
                        )
                    ),
                    timeBlock
                )
            )
            val entity = HttpEntity(payload, slackHeaders)
            restTemplate.exchange(slackProperties.postChatUrl, HttpMethod.POST, entity, String::class.java)
        } catch (e: Exception) {
            log.error { "Failed to send slack message. ${e.message}" }
        }
    }

    fun KLogger.nDebug(message: String) {
        debug { message }
        sendSlackMessage(LogLevel.DEBUG, message)
    }

    fun KLogger.nInfo(message: String) {
        error { message }
        sendSlackMessage(LogLevel.INFO, message)
    }

    fun KLogger.nError(message: String) {
        error { message }
        sendSlackMessage(LogLevel.ERROR, message)
    }

    fun KLogger.nError(exception: BaseException) {
        val errorType = exception.errorType
        error { "Exception Message: ${errorType.message} Detail : ${exception.stackTraceToString()}" }
        sendSlackMessage(exception)
    }

    inline val <reified T> T.log: KLogger
        get() = KotlinLogging.logger(T::class.java.name)
}

