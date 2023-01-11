package com.groupal.ecommerce.auth.repository

import com.groupal.ecommerce.auth.models.ERole
import com.groupal.ecommerce.auth.models.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RoleRepository : JpaRepository<Role?, Long?> {
    fun findByName(name: ERole?): Optional<Role?>?
}