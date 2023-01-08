package com.groupal.ecommerce.user.application.port.out

import com.groupal.ecommerce.user.domain.User

interface CreateUserRepositoryPort {
    fun createUser(user: User): User
}