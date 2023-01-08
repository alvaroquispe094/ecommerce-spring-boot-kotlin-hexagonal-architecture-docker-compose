package com.groupal.ecommerce.user.application.port.`in`

import com.groupal.ecommerce.user.domain.User

interface FindAllUsersInPort {
    fun execute(): List<User>
}