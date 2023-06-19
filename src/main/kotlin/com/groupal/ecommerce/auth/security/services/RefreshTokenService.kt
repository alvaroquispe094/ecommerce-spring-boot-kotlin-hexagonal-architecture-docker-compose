package com.groupal.ecommerce.auth.security.services

import com.groupal.ecommerce.auth.exception.TokenRefreshException
import com.groupal.ecommerce.auth.models.RefreshToken
import com.groupal.ecommerce.auth.repository.RefreshTokenRepository
import com.groupal.ecommerce.auth.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.*

@Service
class RefreshTokenService {
    @Value("\${jwt.refreshExpiration}")
    private val refreshTokenDurationMs: Long? = null

    @Autowired
    private val refreshTokenRepository: RefreshTokenRepository? = null

    @Autowired
    private val userRepository: UserRepository? = null

    fun findByToken(token: String): Optional<RefreshToken> {
        return refreshTokenRepository!!.findByToken(token)
    }

    fun createRefreshToken(userId: Long): RefreshToken {
        var refreshToken = RefreshToken()
        refreshToken.user = userRepository!!.findById(userId).get()
        refreshToken.expiryDate = Instant.now().plusMillis(refreshTokenDurationMs!!)
        refreshToken.token = (UUID.randomUUID().toString())
        refreshToken = refreshTokenRepository!!.save(refreshToken)
        return refreshToken
    }

    fun verifyExpiration(token: RefreshToken): RefreshToken {
        if (token.expiryDate!! < Instant.now()) {
            refreshTokenRepository?.delete(token)
            throw TokenRefreshException(token.token, "Refresh token was expired. Please make a new signin request")
        }
        return token
    }

    @Transactional
    fun deleteByUserId(userId: Long): Int {
        return refreshTokenRepository!!.deleteByUser(userRepository!!.findById(userId).get())
    }
}