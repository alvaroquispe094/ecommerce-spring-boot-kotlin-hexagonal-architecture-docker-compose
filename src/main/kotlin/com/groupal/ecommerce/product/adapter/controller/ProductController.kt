package com.groupal.ecommerce.product.adapter.controller

import com.groupal.ecommerce.product.adapter.controller.model.ProductRest
import com.groupal.ecommerce.product.application.port.`in`.CreateProductInPort
import com.groupal.ecommerce.product.application.port.`in`.DeleteProductInPort
import com.groupal.ecommerce.product.application.port.`in`.FindAllProductsInPort
import com.groupal.ecommerce.product.application.port.`in`.FindProductByIdInPort
import com.groupal.ecommerce.shared.CompanionLogger

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class ProductController(
    private val findAllCountriesInPort: FindAllProductsInPort,
    private val findCountryByIdInPort: FindProductByIdInPort,
    private val createCountryInPort: CreateProductInPort,
    private val deleteCountryInPort: DeleteProductInPort,
) {

    @GetMapping("/product")
    fun findAll(): ResponseEntity< List<ProductRest> > = run {
        logger.info("Llamada al controller api/v1/countries")
        findAllCountriesInPort.execute()
            .map { ProductRest.from(it) }
            .let { ResponseEntity.status(HttpStatus.OK).body(it) }
            .log { info("Fin a llamada al controller /api/v1/countries/{}", it) }
    }

    @GetMapping("/product/{id}")
    fun findById(@PathVariable("id") id: Long): ResponseEntity<ProductRest> = id
        .log { info("Llamada al controller /api/v1/product/id/{}", it) }
        .let { findCountryByIdInPort.execute(it) }
        .let { p -> ProductRest.from(p) }
        .let { ResponseEntity.status(HttpStatus.OK).body(it) }
        .log { info("Fin a llamada al controller /api/v1/product/id/{}", it) }

    @PostMapping("/product")
    fun create(@RequestBody @Validated req: ProductRest): ResponseEntity<ProductRest> = req
        .log { info("Llamada a controller de creacion de producto") }
        .let { req.toDomain() }
        .let { createCountryInPort.execute(it) }
        .let { p -> ProductRest.from(p) }
        .let { ResponseEntity.status(HttpStatus.CREATED).body(it) }
        .log { info("Fin a llamada a controller de creacion de producto {}", it) }

    @DeleteMapping("/product/{id}")
    fun delete(@PathVariable("id") id: Long): ResponseEntity<HttpStatus> = id
        .log { info("Llamada delete al controller /api/v1/product/id/{}", it) }
        .let { deleteCountryInPort.execute(it) }
        //.let { p -> CountriesRest.from(p) }
        .let { ResponseEntity<HttpStatus>( HttpStatus.OK) }
        .log { info("Fin a llamada al controller /api/v1/product/id/{}", it) }

    companion object: CompanionLogger()

}