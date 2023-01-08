package com.groupal.ecommerce.user.application.usecase

import com.groupal.ecommerce.shared.CompanionLogger
import com.groupal.ecommerce.user.application.port.`in`.CreateUserInPort
import com.groupal.ecommerce.user.application.port.out.CreateUserRepositoryPort
import com.groupal.ecommerce.user.domain.User
import org.springframework.stereotype.Component

@Component
class CreateUserUseCase(
    private val createUserRepositoryPort: CreateUserRepositoryPort,
): CreateUserInPort {

    override fun execute(user: User): User = user
        .log { info("Inicio de proceso de creación de user con body request : $it") }
        .let { createUserRepositoryPort.createUser(user) }
        .log { info("Fin de proceso de creación de user con response : $it") }

    companion object: CompanionLogger()
}