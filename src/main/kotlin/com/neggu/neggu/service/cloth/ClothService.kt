package com.neggu.neggu.service.cloth

import com.neggu.neggu.config.LoggerConfig.log
import com.neggu.neggu.config.LoggerConfig.nInfo
import com.neggu.neggu.dto.cloth.ClothRegisterRequest
import com.neggu.neggu.exception.ErrorType
import com.neggu.neggu.exception.NotFoundException
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

@Service
class ClothService(
    private val clothRepository: ClothRepository,
    private val brandRepository: BrandRepository,
    private val s3Service: S3Service,
    private val userRepository: UserRepository,
    private val imageColorService: ImageColorService
) {

    fun getClothPage(user: User, size:Int, page: Int, sortProperty : String = "createdAt"): Page<Cloth> {
        val pageable = PageRequest.of(page, size, Sort.by(sortProperty).descending())
        return clothRepository.findAllByAccountId(user.id, pageable)
    }

    fun getCloth(id: ObjectId): Cloth {
        return clothRepository.findByIdOrNull(id) ?: throw NotFoundException(ErrorType.NotFoundCloth)
    }

    @Transactional
    fun postCloth(user: User, images: MultipartFile, registerRequest: ClothRegisterRequest): Cloth {
        val fileName = s3Service.uploadFile(user, images)
        val colorCode = imageColorService.pickColor(images)
        val cloth = Cloth(
            accountId = user.id!!,
            imageUrl = fileName,
            category = registerRequest.category,
            subCategory = registerRequest.subCategory,
            mood = registerRequest.mood,
            brand = registerRequest.brand,
            priceRange = registerRequest.priceRange,
            memo = registerRequest.memo,
            isPurchase = registerRequest.isPurchase,
            link = registerRequest.link,
            name = "아직 구현 중입니다 (아디다스-회색-후드집업)", // TODO: 구현 필요
            colorCode = colorCode
        )

        val savedCloth = clothRepository.save(cloth)
        userRepository.save(user.copy(clothes = user.clothes + savedCloth.id!!))
        return savedCloth.also {
            log.nInfo("Save Cloth(${it.id}) saved by user ${user.id}\n Cloth Info : $it")
        }
    }

    @Transactional
    fun deleteCloth(user: User, objectId: ObjectId): Cloth {
        val cloth = clothRepository.findByIdOrNull(objectId) ?: throw NotFoundException(ErrorType.NotFoundCloth)
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
}