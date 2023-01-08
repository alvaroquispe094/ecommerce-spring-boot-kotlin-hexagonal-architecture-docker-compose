package com.groupal.ecommerce.user.domain

data class User(
    val id: Long?,
    val firstName: String,
    val lastName: String,
    val userName: String,
    val password: String,
    val email: String,
    val gender: String,
    val birthDate: String,
    val phone: String,
)