package com.groupal.ecommerce.user.application.port.out

interface DeleteUserRepositoryPort {
    fun deleteUser(userId: Long)
}