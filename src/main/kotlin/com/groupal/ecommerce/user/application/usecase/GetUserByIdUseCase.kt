package com.groupal.ecommerce.user.application.usecase

import com.groupal.ecommerce.shared.CompanionLogger
import com.groupal.ecommerce.user.application.port.`in`.FindUserByIdInPort
import com.groupal.ecommerce.user.application.port.out.FindUserByIdRepositoryPort
import com.groupal.ecommerce.user.domain.User
import org.springframework.stereotype.Component

@Component
class GetUserByIdUseCase(
    private val findUserByIdRepositoryPort: FindUserByIdRepositoryPort,
): FindUserByIdInPort {

    override fun execute(id: Long): User = id
        .log {  info("Inicio de proceso de busqueda de country con id = {}", it) }
        .let { findUserByIdRepositoryPort.findUserById(it) }
        .log { info("Fin de proceso de busqueda de country - response : $it") }

    companion object: CompanionLogger()
}