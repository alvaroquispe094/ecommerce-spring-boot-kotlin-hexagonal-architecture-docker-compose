package com.groupal.ecommerce.auth.payload.request

import javax.validation.constraints.NotBlank

class TokenRefreshRequest {
    var refreshToken: @NotBlank String? = null
}