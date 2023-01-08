package com.groupal.ecommerce.user.application.usecase

import com.groupal.ecommerce.product.application.usecase.CreateProductUseCase.Companion.log
import com.groupal.ecommerce.user.application.port.`in`.FindAllUsersInPort
import com.groupal.ecommerce.user.application.port.out.FindAllUsersRepositoryPort
import com.groupal.ecommerce.user.domain.User
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class GetAllUsersUseCase(
    private val findAllUsersRepositoryPort: FindAllUsersRepositoryPort,
): FindAllUsersInPort {

    private val logger = LoggerFactory.getLogger(GetAllUsersUseCase::class.java)

    override fun execute(): List<User> = run {
        logger.info("Inicio de proceso de busqueda de users")
            .let { findAllUsersRepositoryPort.findAllUsers() }
            .log { info("Fin de proceso de busqueda de users") }

    }
}