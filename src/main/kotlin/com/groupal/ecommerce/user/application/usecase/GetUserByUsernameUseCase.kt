/*
package com.groupal.ecommerce.user.application.usecase

import com.groupal.ecommerce.shared.CompanionLogger
import com.groupal.ecommerce.user.application.port.`in`.FindUserByIdInPort
import com.groupal.ecommerce.user.application.port.`in`.FindUserByUsernameInPort
import com.groupal.ecommerce.user.application.port.out.FindUserByIdRepositoryPort
import com.groupal.ecommerce.user.application.port.out.FindUserByUsernameRepositoryPort
import com.groupal.ecommerce.user.domain.User
import org.springframework.stereotype.Component

@Component
class GetUserByUsernameUseCase(
    private val findUserByUsernameRepositoryPort: FindUserByUsernameRepositoryPort,
): FindUserByUsernameInPort {

    override fun execute(username: String): User = username
        .log {  info("Inicio de proceso de busqueda de country con username = {}", it) }
        .let { findUserByUsernameRepositoryPort.findUserByUsername(it) }
        .log { info("Fin de proceso de busqueda de user - response : $it") }

    companion object: CompanionLogger()
}*/
