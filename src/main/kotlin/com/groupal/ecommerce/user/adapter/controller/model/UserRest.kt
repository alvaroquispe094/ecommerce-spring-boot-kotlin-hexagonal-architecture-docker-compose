package com.groupal.ecommerce.user.adapter.controller.model

import com.groupal.ecommerce.user.domain.User
import javax.validation.Valid
import javax.validation.constraints.NotBlank

data class UserRest(
    @get:Valid val id: Long?,
    @get:Valid val firstName: String,
    @get:Valid val lastName: String,
    @get:NotBlank val userName: String,
    @get:Valid val password: String,
    @get:Valid val email: String,
    @get:Valid val gender: String,
    @get:Valid val birthDate: String,
    @get:Valid val phone: String,
) {
    fun toDomain() =
        User(
            id = id,
            firstName = firstName,
            lastName = lastName,
            userName = userName,
            password = password,
            email = email,
            gender = gender,
            birthDate = birthDate,
            phone = phone,
        )

    companion object {
        infix fun from(user: User): UserRest {
            return UserRest(
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
}