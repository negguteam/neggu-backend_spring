package com.neggu.neggu.service

import com.neggu.neggu.dto.cloth.ClothRegisterRequest
import com.neggu.neggu.exception.ErrorType
import com.neggu.neggu.exception.NotFoundException
import com.neggu.neggu.exception.UnAuthorizedException
import com.neggu.neggu.model.cloth.*
import com.neggu.neggu.model.user.User
import com.neggu.neggu.repository.BrandRepository
import com.neggu.neggu.repository.ClothRepository
import com.neggu.neggu.service.aws.S3Service
import org.bson.types.ObjectId
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class ClothService(
    private val clothRepository: ClothRepository,
    private val brandRepository: BrandRepository,
    private val s3Service: S3Service
) {

    fun getClothPage(user: User, size:Int, page: Int, sortProperty : String = "createdAt"): Page<Cloth> {
        val pageable = PageRequest.of(page, size, Sort.by(sortProperty).descending())
        return clothRepository.findAllByAccountId(user.id, pageable)
    }

    fun getCloth(id: ObjectId): Cloth {
        return clothRepository.findByIdOrNull(id) ?: throw NotFoundException(ErrorType.NotFoundCloth)
    }

    fun postCloth(user: User, images: MultipartFile, registerRequest: ClothRegisterRequest): Cloth {
        val fileName = s3Service.uploadFile(user, images)
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
            colorCode = "구현 중입니다. (#fffffff)" // TODO: 구현 필요
        )
        return clothRepository.save(cloth)
    }

    fun deleteCloth(user: User, objectId: ObjectId): Cloth {
        val cloth = clothRepository.findByIdOrNull(objectId) ?: throw NotFoundException(ErrorType.NotFoundCloth)
        if (cloth.accountId != user.id) { throw UnAuthorizedException(ErrorType.InvalidIdToken) }
        clothRepository.delete(cloth)
        return cloth
    }

    fun getBrands(): List<ClothBrand> {
        return brandRepository.findAll()
    }
}