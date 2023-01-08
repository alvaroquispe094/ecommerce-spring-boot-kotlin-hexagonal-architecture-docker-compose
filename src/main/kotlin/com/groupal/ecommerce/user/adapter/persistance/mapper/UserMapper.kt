package com.groupal.ecommerce.user.adapter.persistance.mapper

import com.groupal.ecommerce.user.adapter.persistance.model.UserEntity
import com.groupal.ecommerce.user.domain.User

object UserMapper {

    fun toUserEntity(user: User): UserEntity {
        return UserEntity(
            id = user.id,
            firstName = user.firstName,
            lastName = user.lastName,
            userName = user.userName,
            password = user.password,
            email = user.email,
            gender = user.gender,
            birthDate = user.birthDate,
            phone = user.phone,
        )
    }
}