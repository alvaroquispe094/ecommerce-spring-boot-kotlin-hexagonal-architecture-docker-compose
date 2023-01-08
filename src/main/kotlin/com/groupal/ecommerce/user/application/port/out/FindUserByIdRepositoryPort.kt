package com.groupal.ecommerce.user.application.port.out

import com.groupal.ecommerce.user.domain.User

interface FindUserByIdRepositoryPort {
    fun findUserById(userId: Long): User
}