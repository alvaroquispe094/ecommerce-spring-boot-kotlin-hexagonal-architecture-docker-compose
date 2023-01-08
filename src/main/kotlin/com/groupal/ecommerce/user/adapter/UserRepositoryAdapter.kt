package com.groupal.ecommerce.user.adapter


import com.groupal.ecommerce.user.adapter.persistance.mapper.UserMapper
import com.groupal.ecommerce.user.adapter.persistance.repository.SpringDataUserRepository
import com.groupal.ecommerce.user.domain.User
import com.groupal.ecommerce.shared.CompanionLogger
import com.groupal.ecommerce.shared.config.MessageError
import com.groupal.ecommerce.shared.config.exception.BadArgumentException
import com.groupal.ecommerce.shared.config.exception.DaoException
import com.groupal.ecommerce.shared.config.exception.ResourceNotFoundException
import com.groupal.ecommerce.user.application.port.out.*
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryAdapter(
    val repository: SpringDataUserRepository
): FindAllUsersRepositoryPort, FindUserByIdRepositoryPort, CreateUserRepositoryPort, DeleteUserRepositoryPort {

    override fun findAllUsers(): List<User> {
        return repository.findAll().map { it.toUserDomain() }
    }

    override fun findUserById(userId: Long) = try {
        userId
            .log { info("UserWebService Request - id = {}", userId) }
            .let { repository.findById(it) }
            .orElseThrow { ResourceNotFoundException(MessageError.RESOURCE_NOT_FOUND.errorCode, "No se encontro el user con id $userId") }
            .toUserDomain()
            .log { info("UserWebService Response: $it") }
    }catch (e: ResourceNotFoundException){
        logger.error("Error al acceder al recurso con id : $userId")
        throw ResourceNotFoundException(MessageError.RESOURCE_NOT_FOUND.errorCode, "No se encontro el user con id = $userId")
    }catch (e: IllegalArgumentException){
        logger.error("Error al acceder al recurso, el id es null")
        throw DaoException(MessageError.BAD_REQUEST.errorCode, "No se pudo encontrar el user debido a que el id es null")
    }

    override fun createUser(user: User) = try {
        user
            .log { info("UserWebService Request: {}", user) }
            .let { UserMapper.toUserEntity(it) }
            .let { repository.save(it) }
            .toUserDomain()
            .log {  info("UserWebService Response: $it")}
    } catch (e: IllegalArgumentException){
        logger.error("Error al grabar al recurso, la entidad es null")
        throw DaoException(MessageError.BAD_REQUEST.errorCode, "No se pudo grabar el country debido a que la entidad dominio es null")
    }

    override fun deleteUser(userId: Long) = try {
        userId
            .log { info("UserWebService delete Request - id = {}", userId) }
            .let { repository.deleteById(it) }
            .log {  info("UserWebService delete Response")}
    }catch (e: ResourceNotFoundException){
        logger.error("Error al acceder al recurso con id : $userId")
        throw ResourceNotFoundException(MessageError.RESOURCE_NOT_FOUND.errorCode, "No se encontro el user con id = $userId")
    }catch (e: Exception){
        logger.error("Error al acceder al recurso, el id es null")
        throw BadArgumentException(MessageError.ILLEGAL_ARGUMENT.errorCode, "No se pudo encontrar el user debido a que el id no existe")
    }

    companion object: CompanionLogger()
}