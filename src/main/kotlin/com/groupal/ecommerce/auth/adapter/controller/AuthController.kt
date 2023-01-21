package com.groupal.ecommerce.auth.adapter.controller

import com.groupal.ecommerce.auth.models.ERole
import com.groupal.ecommerce.auth.models.Role
import com.groupal.ecommerce.auth.models.User
import com.groupal.ecommerce.auth.payload.request.LoginRequest
import com.groupal.ecommerce.auth.payload.request.SignupRequest
import com.groupal.ecommerce.auth.payload.response.JwtResponse
import com.groupal.ecommerce.auth.payload.response.MessageResponse
import com.groupal.ecommerce.auth.repository.RoleRepository
import com.groupal.ecommerce.auth.repository.UserRepository
import com.groupal.ecommerce.auth.security.jwt.JwtUtils
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
        return ResponseEntity.ok<Any>(
            JwtResponse(
                jwt,
                userDetails.id!!,
                userDetails.username,
                userDetails.email!!,
                roles
            )
        )
    }

    @PostMapping("/auth/refresh")
    fun authenticateRefreshUser(): ResponseEntity<*>? {

        val user = "mgomez@gmail.com"
        val pass = "Abcd#100"

        val authentication = authenticationManager!!.authenticate(
            UsernamePasswordAuthenticationToken
                (user, pass)
        )
        SecurityContextHolder.getContext().authentication = authentication
        val jwt: String = jwtUtils!!.generateJwtToken(authentication)
        val userDetails: UserDetailsImpl = authentication.principal as UserDetailsImpl
        val roles: List<String> = userDetails.authorities.stream()
            .map { item -> item.authority }
            .collect(Collectors.toList())
        return ResponseEntity.ok<Any>(
            JwtResponse(
                jwt,
                userDetails.id!!,
                userDetails.username,
                userDetails.email!!,
                roles
            )
        )
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
    companion object: CompanionLogger()

}