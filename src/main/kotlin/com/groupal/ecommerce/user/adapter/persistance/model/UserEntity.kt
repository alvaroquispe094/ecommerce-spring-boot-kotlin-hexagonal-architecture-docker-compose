package com.groupal.ecommerce.user.adapter.persistance.model

import com.groupal.ecommerce.user.domain.User
import javax.persistence.*

@Entity
@Table(name="Users")
data class UserEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long?,
    @Column(name = "first_name") val firstName: String,
    @Column(name = "last_name") val lastName: String,
    @Column(name = "user_name") val userName: String,
    val password: String,
    val email: String,
    val gender: String,
    @Column(name = "birth_date") val birthDate: String,
    val phone: String,
){

    fun toUserDomain(): User {
        return User(
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
    }

}
