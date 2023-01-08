package com.groupal.ecommerce.user.application.port.`in`

import com.groupal.ecommerce.user.domain.User

interface FindUserByIdInPort {
    fun execute(id: Long): User
}