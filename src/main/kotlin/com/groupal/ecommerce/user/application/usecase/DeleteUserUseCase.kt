package com.groupal.ecommerce.user.application.usecase

import com.groupal.ecommerce.shared.CompanionLogger
import com.groupal.ecommerce.user.application.port.`in`.DeleteUserInPort
import com.groupal.ecommerce.user.application.port.out.DeleteUserRepositoryPort
import org.springframework.stereotype.Component

@Component
class DeleteUserUseCase(
    private val deleteUserRepositoryPort: DeleteUserRepositoryPort,
): DeleteUserInPort {

    override fun execute(idUser: Long) =
        idUser
            .log {  info("Inicio de proceso de delete de user con id = {}", it) }
            .let { deleteUserRepositoryPort.deleteUser(it) }
            .log { info("Fin de proceso de delete de user - response : $it") }

    companion object: CompanionLogger()
}