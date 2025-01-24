package com.neggu.neggu.service.cloth

import com.neggu.neggu.config.LoggerConfig.log
import com.neggu.neggu.config.LoggerConfig.nInfo
import com.neggu.neggu.dto.cloth.ClothModifyRequest
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
import com.neggu.neggu.util.toObjectId
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
    private val colorFinder: ColorFinder
) {

    fun getClothes(user: User, category: Category?, colorGroup: ColorGroup?, mood: Mood?, size: Int, page: Int, sortProperty : String = "createdAt"): Page<Cloth> {
        val pageable = PageRequest.of(page, size, Sort.by(sortProperty).descending())
        return clothRepository.findClothesDynamic(user.id!!, category, colorGroup?.getColors(), mood, pageable)
    }

    fun getCloth(id: ObjectId): Cloth {
        return clothRepository.findByIdOrNull(id) ?: throw ServerException(ErrorType.NotFoundCloth)
    }

    @Transactional
    fun registerCloth(user: User, image: MultipartFile, registerRequest: ClothRegisterRequest): Cloth {
        val clothColor = colorFinder.findColor(registerRequest.colorCode)
        val fileName = s3Service.uploadFile(user, image)
        val cloth = registerRequest.toCloth(user.id!!, fileName, clothColor)

        val savedCloth = clothRepository.save(cloth)
        userRepository.save(user.copy(clothes = user.clothes + savedCloth.id!!))
        return savedCloth.also {
            log.nInfo("[Register] Cloth(${it.id}) by user ${user.id}\n Cloth Info : $it")
        }
    }

    @Transactional
    fun modifyCloth(user: User, clothRegisterRequest: ClothModifyRequest): Cloth {
        val accountId = clothRegisterRequest.accountId.toObjectId()
        if (accountId != user.id) { throw UnAuthorizedException(ErrorType.InvalidIdToken) }
        val clothColor = colorFinder.findColor(clothRegisterRequest.colorCode)

        val savedCloth = clothRepository.save(clothRegisterRequest.toCloth(clothColor))
        return clothRepository.save(savedCloth).also {
            log.nInfo("[Update] Cloth(${it.id}) by user ${user.id}\n Cloth Info : $it")
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

    fun getBrands(query: String?): List<ClothBrand> {
        if (query != null) {
            return brandRepository.findByNameContaining(query)
        }
        return brandRepository.findAll()
    }

    fun registerCloth(user: User, cloth:Cloth): Cloth {
        if (cloth.accountId != user.id) { throw UnAuthorizedException(ErrorType.InvalidIdToken) }
        return clothRepository.save(cloth).also {
            log.nInfo("Cloth(${it.id}) updated by user ${user.id}\n Cloth Info : $it")
        }
    }
}