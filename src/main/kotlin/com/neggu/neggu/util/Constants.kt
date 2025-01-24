package com.neggu.neggu.util

import java.time.ZoneId
import java.time.format.DateTimeFormatter

object Constants {

    val DEFAULT_TIME_ZONE: ZoneId = ZoneId.of("Asia/Seoul")
    val DEFAULT_DATETIME_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss")
}