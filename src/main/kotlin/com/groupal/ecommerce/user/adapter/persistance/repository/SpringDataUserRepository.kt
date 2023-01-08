package com.groupal.ecommerce.user.adapter.persistance.repository

import com.groupal.ecommerce.user.adapter.persistance.model.UserEntity
import org.springframework.data.repository.CrudRepository

interface SpringDataUserRepository: CrudRepository<UserEntity, Long>{
}