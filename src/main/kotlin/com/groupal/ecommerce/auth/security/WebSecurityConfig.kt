package com.groupal.ecommerce.auth.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.groupal.ecommerce.auth.security.jwt.AuthEntryPointJwt
import com.groupal.ecommerce.auth.security.jwt.AuthTokenFilter
import com.groupal.ecommerce.auth.security.services.UserDetailsServiceImpl
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfig: WebSecurityConfigurerAdapter() {
    @Autowired
    private val unauthorizedHandler: AuthEntryPointJwt? = null

    @Autowired
    var userDetailsService: UserDetailsServiceImpl? = null

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun authenticationJwtTokenFilter(): AuthTokenFilter {
        return AuthTokenFilter()
    }

    @Bean
    fun accessDeniedHandler(): AccessDeniedHandler? {
        return AccessDeniedHandler { request: HttpServletRequest?, response: HttpServletResponse, ex: AccessDeniedException? ->
            logger.error("Denied error: {}", "Role authentication is required to access this resource")
            response.status = HttpServletResponse.SC_FORBIDDEN
            response.contentType = MediaType.APPLICATION_JSON_VALUE
            val body: MutableMap<String, Any?> = HashMap()
            body["status"] = HttpServletResponse.SC_FORBIDDEN
            body["error"] = "Forbidden"
            body["message"] = "Access denied"
            if (request != null) {
                body["path"] = request.servletPath
            }
            val out = response.outputStream
            ObjectMapper().writeValue(out, body)
            out.flush()
        }
    }

    @Throws(java.lang.Exception::class)
    public override fun configure(authenticationManagerBuilder: AuthenticationManagerBuilder) {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder())
    }

    @Bean
    @Throws(java.lang.Exception::class)
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.cors().and().csrf().disable().exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
            .antMatchers("/api/v1/auth/signin","/api/v1/auth/signup", "/api/v1/auth/refresh").permitAll()
            .antMatchers("/api/v1/user/**").access("hasRole('ROLE_USER') or hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
            .antMatchers("/api/v1/mod/**").access("hasRole('ROLE_MODERATOR') ")
            .antMatchers("/api/v1/product/**").access("hasRole('ADMIN') ")
            .anyRequest().authenticated()
        http.addFilterBefore(
            authenticationJwtTokenFilter(),
            UsernamePasswordAuthenticationFilter::class.java
        )
        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler());
    }

    companion object {
        private val logger = LoggerFactory.getLogger(AuthEntryPointJwt::class.java)
    }
}