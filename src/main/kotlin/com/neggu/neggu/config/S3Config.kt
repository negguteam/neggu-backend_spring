package com.neggu.neggu.config

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.neggu.neggu.config.properties.AwsProperties
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class S3Config(
    private val awsProperties: AwsProperties
) {

    @Bean
    fun amazonS3Client(): AmazonS3 {
        val credentials = BasicAWSCredentials(
            awsProperties.credentials.accessKey,
            awsProperties.credentials.secretKey
        )
        return AmazonS3ClientBuilder
            .standard()
            .withCredentials(AWSStaticCredentialsProvider(credentials))
            .withRegion(awsProperties.region)
            .build()
    }
}