package com.neggu.neggu.service.aws

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.neggu.neggu.config.properties.AwsProperties
import com.neggu.neggu.exception.ErrorType
import com.neggu.neggu.exception.ServerException
import com.neggu.neggu.model.user.User
import com.neggu.neggu.util.Constants
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import java.time.LocalDateTime
import java.util.*


@Service
class S3Service(
    private val amazonS3: AmazonS3,
    private val awsProperties: AwsProperties
) {

    fun uploadFile(user: User, multipartFile: MultipartFile?): String? {
        if (multipartFile == null || multipartFile.isEmpty) { throw ServerException(ErrorType.BadRequest) }
        val fileName = createFileName(user, multipartFile.originalFilename)
        val objectMetadata = ObjectMetadata().apply {
            contentLength = multipartFile.size
            contentType = multipartFile.contentType
        }

        try {
            multipartFile.inputStream.use { inputStream ->
                amazonS3.putObject(PutObjectRequest(awsProperties.s3.bucket, fileName, inputStream, objectMetadata))
            }
        } catch (e: IOException) {
            throw ServerException(ErrorType.ImageUploadFail)
        }

        return amazonS3.getUrl(awsProperties.s3.bucket, fileName).toString()
    }

    fun createFileName(user: User, originalFilename: String?): String {
        val extension = originalFilename?.substringAfterLast(".") ?: throw ServerException(ErrorType.BadRequest)
        val currentTime = LocalDateTime.now(Constants.timeZone).format(Constants.defaultDateFormatter)
        return "${user.id}/${currentTime}_${UUID.randomUUID()}.${extension}"
    }

    fun deleteFile(imageUrl: String?) {
        if (imageUrl == null) { return }
        val decodedUrl = URLDecoder.decode(imageUrl, StandardCharsets.UTF_8)
        val filePath = decodedUrl.removePrefix(getPrefixUrl())
        amazonS3.deleteObject(awsProperties.s3.bucket, filePath)
    }

    private fun getPrefixUrl() = "https://${awsProperties.s3.bucket}.s3.${awsProperties.region}.amazonaws.com/"

}