package com.groupal.ecommerce.user.application.port.out

import com.groupal.ecommerce.user.domain.User

interface FindAllUsersRepositoryPort {
    fun findAllUsers(): List<User>
}