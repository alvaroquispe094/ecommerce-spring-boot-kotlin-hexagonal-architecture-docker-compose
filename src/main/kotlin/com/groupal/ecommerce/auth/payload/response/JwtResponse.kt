package com.groupal.ecommerce.auth.payload.response

class JwtResponse(
    var accessToken: String,
    var id: Long,
    var username: String,
    var email: String,
    var roles: List<String>
) {
    var tokenType = "Bearer"

}