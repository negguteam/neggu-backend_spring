package com.neggu.neggu.util

import java.time.ZoneId
import java.time.format.DateTimeFormatter

object Constants {

    val timeZone: ZoneId = ZoneId.of("Asia/Seoul")
    val defaultDateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
}