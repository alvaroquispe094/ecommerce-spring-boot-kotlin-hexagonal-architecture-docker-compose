package com.groupal.ecommerce.auth.payload.response

class TokenRefreshResponse(var accessToken: String, var refreshToken: String) {
    var tokenType = "Bearer"

}