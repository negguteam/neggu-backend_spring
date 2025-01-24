package com.neggu.neggu.config

import com.neggu.neggu.exception.BaseException
import com.neggu.neggu.util.Constants
import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.logging.LogLevel
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.core.env.Environment
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.concurrent.Executors
import kotlin.properties.Delegates


@Component
@Order(Ordered.LOWEST_PRECEDENCE)
object LoggerConfig {

    private lateinit var slackProperties: SlackProperties
    private lateinit var slackHeaders: HttpHeaders
    private var enable by Delegates.notNull<Boolean>()

    private val restTemplate = RestTemplate()
    private val MAX_LOG_SIZE = 1_000

    private val logDispatcher = Executors.newFixedThreadPool(2).asCoroutineDispatcher()
    private val slackScope = CoroutineScope(logDispatcher)

    @Autowired
    fun init(
        properties: SlackProperties,
        environment: Environment
     ) {
        enable = environment.activeProfiles.any { it == "real" || it == "beta" }
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
                "text" to "Sent at: ${LocalDateTime.now(Constants.timeZone).format(Constants.defaultDateFormatter)}"
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

    private fun sendSlackMessage(message:String, stackTrace: String?, logLevel: LogLevel) = slackScope.launch {
        if (!enable) return@launch
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
                    stackTrace?.let {
                        mapOf(
                            "type" to "section",
                            "text" to mapOf(
                                "type" to "mrkdwn",
                                "text" to "*StackTrace:*\n```$stackTrace```"
                            )
                        )
                    },
                    timeBlock
                )
            )
            val entity = HttpEntity(payload, slackHeaders)
            val result = restTemplate.exchange(slackProperties.postChatUrl, HttpMethod.POST, entity, String::class.java)
            log.info { "Slack message sent successfully. ${result.body}" }
        } catch (e: Exception) {
            e.printStackTrace()
            log.error { "Failed to send slack message. ${e.message}" }
        }
    }

    fun KLogger.nDebug(message: String) {
        debug { message }
        sendSlackMessage(message = message, stackTrace = null, logLevel = LogLevel.DEBUG)
    }

    fun KLogger.nInfo(message: String) {
        info { message }
        sendSlackMessage(message = message, stackTrace = null, logLevel = LogLevel.INFO)
    }

    fun KLogger.nError(message: String) {
        error { message }
        sendSlackMessage(message = message, stackTrace = null, logLevel = LogLevel.ERROR)
    }

    fun KLogger.nError(exception: BaseException) {
        val errorType = exception.errorType
        error { "Exception Message: ${errorType.message} Detail : ${exception.stackTraceToString()}" }
        sendSlackMessage(message = errorType.message, stackTrace = exception.stackTraceToString().take(MAX_LOG_SIZE), logLevel = errorType.logLevel)
    }

    fun KLogger.nError(exception: Exception) {
        error { "Exception Message: ${exception} Detail : ${exception.stackTraceToString()}" }
        sendSlackMessage(message = exception.toString(), stackTrace = exception.stackTraceToString().take(MAX_LOG_SIZE), logLevel = LogLevel.ERROR)
    }

    inline val <reified T> T.log: KLogger
        get() = KotlinLogging.logger(T::class.java.name)
}

