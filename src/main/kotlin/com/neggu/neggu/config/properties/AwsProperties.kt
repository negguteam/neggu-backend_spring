package com.neggu.neggu.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "aws")
data class AwsProperties(
    var credentials: Credentials = Credentials(),
    var s3: S3 = S3(),
    var region: String = "",
) {

    data class Credentials(
        var accessKey: String = "",
        var secretKey: String = "",
    )

    data class S3(
        var bucket: String = "",
    )
}