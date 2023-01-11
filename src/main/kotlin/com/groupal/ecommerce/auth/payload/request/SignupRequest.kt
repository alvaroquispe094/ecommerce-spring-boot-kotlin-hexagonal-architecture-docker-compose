package com.groupal.ecommerce.auth.payload.request

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

class SignupRequest {
    var firstName: @NotBlank @Size(min = 3, max = 20) String? = null
    var lastName: @NotBlank @Size(min = 3, max = 20) String? = null
    var username: @NotBlank @Size(min = 3, max = 20) String? = null
    var email: @NotBlank @Size(max = 50) @Email String? = null
    var roles: Set<String>? = null
        private set
    var password: @NotBlank @Size(min = 6, max = 40) String? = null
    var gender: @NotBlank @Size(min = 3, max = 20) String? = null
    var birthDate: @NotBlank @Size(min = 3, max = 20) String? = null
    var phone: @NotBlank @Size(min = 3, max = 20) String? = null

    fun setRole(roles: Set<String>?) {
        this.roles = roles
    }
}