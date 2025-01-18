package com.neggu.neggu.service.cloth

import com.neggu.neggu.config.LoggerConfig.log
import com.neggu.neggu.config.LoggerConfig.nInfo
import com.neggu.neggu.dto.cloth.ClothRegisterRequest
import com.neggu.neggu.exception.ErrorType
import com.neggu.neggu.exception.ServerException
import com.neggu.neggu.exception.UnAuthorizedException
import com.neggu.neggu.model.cloth.*
import com.neggu.neggu.model.user.User
import com.neggu.neggu.repository.BrandRepository
import com.neggu.neggu.repository.ClothRepository
import com.neggu.neggu.repository.UserRepository
import com.neggu.neggu.service.aws.S3Service
import org.bson.types.ObjectId
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import kotlin.math.pow
import kotlin.math.sqrt

@Service
class ClothService(
    private val clothRepository: ClothRepository,
    private val brandRepository: BrandRepository,
    private val s3Service: S3Service,
    private val userRepository: UserRepository,
) {

    fun getClothes(user: User, size:Int, page: Int, sortProperty : String = "createdAt"): Page<Cloth> {
        val pageable = PageRequest.of(page, size, Sort.by(sortProperty).descending())
        return clothRepository.findAllByAccountId(user.id, pageable)
    }

    fun getClothes(
        user: User,
        filterCategory: Category?,
        colorGroup: ColorGroup,
        size: Int,
        page: Int,
    ): Page<Cloth> {
        val pageable = PageRequest.of(page, size, Sort.by("createdAt").descending())
        if (filterCategory == null) {
            return clothRepository.findAllByAccountIdAndColorIn(user.id, colorGroup.getColors(), pageable)
        }
        return clothRepository.findAllByAccountIdAndCategoryAndColorIn(user.id, filterCategory, colorGroup.getColors(), pageable)
    }

    fun getCloth(id: ObjectId): Cloth {
        return clothRepository.findByIdOrNull(id) ?: throw ServerException(ErrorType.NotFoundCloth)
    }

    @Transactional
    fun postCloth(user: User, images: MultipartFile, registerRequest: ClothRegisterRequest): Cloth {
        val clothColor = findClothColor(registerRequest.colorCode)
        val fileName = s3Service.uploadFile(user, images)
        val cloth = registerRequest.toCloth(user.id!!, fileName, clothColor)

        val savedCloth = clothRepository.save(cloth)
        userRepository.save(user.copy(clothes = user.clothes + savedCloth.id!!))
        return savedCloth.also {
            log.nInfo("Save Cloth(${it.id}) saved by user ${user.id}\n Cloth Info : $it")
        }
    }

    @Transactional
    fun deleteCloth(user: User, objectId: ObjectId): Cloth {
        val cloth = clothRepository.findByIdOrNull(objectId) ?: throw ServerException(ErrorType.NotFoundCloth)
        if (cloth.accountId != user.id) { throw UnAuthorizedException(ErrorType.InvalidIdToken) }
        clothRepository.delete(cloth)
        s3Service.deleteFile(cloth.imageUrl)
        userRepository.save(user.copy(clothes = user.clothes.filter { it != cloth.id }))
        return cloth.also {
            log.nInfo("Cloth(${it.id}) deleted by user ${user.id}\n Cloth Info : $it")
        }
    }

    fun getBrands(): List<ClothBrand> {
        return brandRepository.findAll()
    }

    private fun findClothColor(hex: String): ClothColor {
        val inputRgb = RGBColor.fromHex(hex)
        return ClothColor.entries.toTypedArray().minBy { color ->
            val colorHex = color.hex ?: return@minBy Double.MAX_VALUE
            calculateEuclideanDistance(inputRgb, RGBColor.fromHex(colorHex))
        }
    }

    private fun calculateEuclideanDistance(rgb1: RGBColor, rgb2: RGBColor): Double {
        return sqrt(
            (rgb1.red - rgb2.red).toDouble().pow(2) +
                    (rgb1.green - rgb2.green).toDouble().pow(2) +
                    (rgb1.blue - rgb2.blue).toDouble().pow(2)
        )
    }

    fun postCloth(user: User, cloth:Cloth): Cloth {
        if (cloth.accountId != user.id) { throw UnAuthorizedException(ErrorType.InvalidIdToken) }
        return clothRepository.save(cloth).also {
            log.nInfo("Cloth(${it.id}) updated by user ${user.id}\n Cloth Info : $it")
        }
    }
}