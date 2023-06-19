package com.groupal.ecommerce.auth.adapter.controller

import com.groupal.ecommerce.auth.exception.TokenRefreshException
import com.groupal.ecommerce.auth.models.ERole
import com.groupal.ecommerce.auth.models.RefreshToken
import com.groupal.ecommerce.auth.models.Role
import com.groupal.ecommerce.auth.models.User
import com.groupal.ecommerce.auth.payload.request.LoginRequest
import com.groupal.ecommerce.auth.payload.request.SignupRequest
import com.groupal.ecommerce.auth.payload.request.TokenRefreshRequest
import com.groupal.ecommerce.auth.payload.response.JwtResponse
import com.groupal.ecommerce.auth.payload.response.MessageResponse
import com.groupal.ecommerce.auth.payload.response.TokenRefreshResponse
import com.groupal.ecommerce.auth.repository.RoleRepository
import com.groupal.ecommerce.auth.repository.UserRepository
import com.groupal.ecommerce.auth.security.jwt.JwtUtils
import com.groupal.ecommerce.auth.security.services.RefreshTokenService
import com.groupal.ecommerce.auth.security.services.UserDetailsImpl
import com.groupal.ecommerce.shared.CompanionLogger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import java.util.function.Consumer
import java.util.stream.Collectors
import javax.validation.Valid
import javax.validation.constraints.NotBlank

@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
class AuthController {
    @Autowired
    var authenticationManager: AuthenticationManager? = null

    @Autowired
    var userRepository: UserRepository? = null

    @Autowired
    var roleRepository: RoleRepository? = null

    @Autowired
    var encoder: PasswordEncoder? = null

    @Autowired
    var jwtUtils: JwtUtils? = null

    @Autowired
    var refreshTokenService: RefreshTokenService? = null

    @PostMapping("/auth/signin")
    fun authenticateUser(@RequestBody loginRequest: @Valid LoginRequest?): ResponseEntity<*>? {

        val authentication = authenticationManager!!.authenticate(
            UsernamePasswordAuthenticationToken
                (loginRequest!!.username, loginRequest.password)
        )
        SecurityContextHolder.getContext().authentication = authentication

        val jwt: String = jwtUtils!!.generateJwtToken(authentication)

        val userDetails: UserDetailsImpl = authentication.principal as UserDetailsImpl

        val roles: List<String> = userDetails.authorities.stream()
            .map { item -> item.authority }
            .collect(Collectors.toList())

        val refreshToken = refreshTokenService!!.createRefreshToken(userDetails.id!!)

        return ResponseEntity.ok(JwtResponse(jwt, refreshToken.token!!, userDetails.id,
        userDetails.username, userDetails.email!!, roles))

    }

    @PostMapping("/auth/refresh")
    fun refreshtoken(@RequestBody request: @Valid TokenRefreshRequest?): ResponseEntity<*>? {
        val requestRefreshToken: @NotBlank String? = request!!.refreshToken
        return refreshTokenService!!.findByToken(requestRefreshToken!!)
            .map(refreshTokenService!!::verifyExpiration)
            .map(RefreshToken::user)
            .map { user ->
                val token: String? = jwtUtils!!.generateTokenFromUsername(user!!.username)
                //change generate a new refresh token para continuar refrescando lasesion mientras use la app
                val newRefreshToken = refreshTokenService!!.createRefreshToken(user.id!!)
                ResponseEntity.ok(TokenRefreshResponse(token!!, newRefreshToken.token!!))
            }
            .orElseThrow {
                TokenRefreshException(
                    requestRefreshToken,
                    "Refresh token is not in database!"
                )
            }
    }

    @PostMapping("/auth/signup")
    fun registerUser(@RequestBody signUpRequest: @Valid SignupRequest?): ResponseEntity<*>? {
        if (userRepository!!.existsByUsername(signUpRequest?.username)!!) {
            return ResponseEntity
                .badRequest()
                .body<Any>(MessageResponse("Error: Username is already taken!"))
        }
        if (userRepository!!.existsByEmail(signUpRequest?.email)!!) {
            return ResponseEntity
                .badRequest()
                .body<Any>(MessageResponse("Error: Email is already in use!"))
        }

        // Create new user's account
        val user = User(
            signUpRequest!!.firstName,
            signUpRequest!!.lastName,
            signUpRequest!!.username,
            signUpRequest!!.email,
            encoder!!.encode(signUpRequest.password),
            signUpRequest!!.gender,
            signUpRequest!!.birthDate,
            signUpRequest!!.phone
        )

        val strRoles = signUpRequest.roles
        val roles: MutableSet<Role> = HashSet<Role>()

        if (strRoles == null) {
            val userRole: Role = roleRepository!!.findByName(ERole.ROLE_USER)
                ?.orElseThrow { RuntimeException("Error: Role is not found.") }!!
            roles.add(userRole)
        } else {
            strRoles.forEach(Consumer { role: String? ->
                when (role) {
                    "admin" -> {
                        val adminRole: Role = roleRepository!!.findByName(ERole.ROLE_ADMIN)
                            ?.orElseThrow { RuntimeException("Error: Role is not found.") }!!
                        roles.add(adminRole)
                    }

                    "mod" -> {
                        val modRole: Role = roleRepository!!.findByName(ERole.ROLE_MODERATOR)
                            ?.orElseThrow { RuntimeException("Error: Role is not found.") }!!
                        roles.add(modRole)
                    }

                    else -> {
                        val userRole: Role = roleRepository!!.findByName(ERole.ROLE_USER)
                            ?.orElseThrow { RuntimeException("Error: Role is not found.") }!!
                        roles.add(userRole)
                    }
                }
            })
        }
        user.roles = roles
        userRepository!!.save(user)
        return ResponseEntity.ok<Any>(MessageResponse("User registered successfully!"))
    }

    @PostMapping("/signout")
    fun logoutUser(): ResponseEntity<*>? {
        val userDetails = SecurityContextHolder.getContext().authentication.principal as UserDetailsImpl
        val userId: Long = userDetails.id!!
        refreshTokenService!!.deleteByUserId(userId)
        return ResponseEntity.ok<Any>(MessageResponse("Log out successful!"))
    }

    companion object: CompanionLogger()

}