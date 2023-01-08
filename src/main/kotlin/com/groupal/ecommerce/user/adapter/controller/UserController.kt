package com.groupal.ecommerce.user.adapter.controller

import com.groupal.ecommerce.shared.CompanionLogger
import com.groupal.ecommerce.user.adapter.controller.model.UserRest
import com.groupal.ecommerce.user.application.port.`in`.CreateUserInPort
import com.groupal.ecommerce.user.application.port.`in`.DeleteUserInPort
import com.groupal.ecommerce.user.application.port.`in`.FindAllUsersInPort
import com.groupal.ecommerce.user.application.port.`in`.FindUserByIdInPort

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class UserController(
    private val findAllUsersInPort: FindAllUsersInPort,
    private val findUserByIdInPort: FindUserByIdInPort,
    private val createUserInPort: CreateUserInPort,
    private val deleteUserInPort: DeleteUserInPort,
) {

    @GetMapping("/user")
    fun findAll(): ResponseEntity< List<UserRest> > = run {
        logger.info("Llamada al controller api/v1/user")
        findAllUsersInPort.execute()
            .map { UserRest.from(it) }
            .let { ResponseEntity.status(HttpStatus.OK).body(it) }
            .log { info("Fin a llamada al controller /api/v1/countries/{}", it) }
    }

    @GetMapping("/user/{id}")
    fun findById(@PathVariable("id") id: Long): ResponseEntity<UserRest> = id
        .log { info("Llamada al controller /api/v1/user/id/{}", it) }
        .let { findUserByIdInPort.execute(it) }
        .let { p -> UserRest.from(p) }
        .let { ResponseEntity.status(HttpStatus.OK).body(it) }
        .log { info("Fin a llamada al controller /api/v1/countries/id/{}", it) }

    @PostMapping("/user")
    fun create(@RequestBody @Validated req: UserRest): ResponseEntity<UserRest> = req
        .log { info("Llamada a controller de creacion de user") }
        .let { req.toDomain() }
        .let { createUserInPort.execute(it) }
        .let { p -> UserRest.from(p) }
        .let { ResponseEntity.status(HttpStatus.CREATED).body(it) }
        .log { info("Fin a llamada a controller de creacion de user {}", it) }

    @DeleteMapping("/user/{id}")
    fun delete(@PathVariable("id") id: Long): ResponseEntity<HttpStatus> = id
        .log { info("Llamada delete al controller /api/v1/user/id/{}", it) }
        .let { deleteUserInPort.execute(it) }
        //.let { p -> CountriesRest.from(p) }
        .let { ResponseEntity<HttpStatus>( HttpStatus.OK) }
        .log { info("Fin a llamada al controller /api/v1/user/id/{}", it) }

    companion object: CompanionLogger()

}