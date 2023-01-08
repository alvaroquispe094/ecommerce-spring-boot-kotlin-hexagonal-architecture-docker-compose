package com.groupal.ecommerce.product.application.usecase

import com.groupal.ecommerce.product.application.port.`in`.CreateProductInPort
import com.groupal.ecommerce.product.application.port.out.CreateProductRepositoryPort
import com.groupal.ecommerce.product.domain.Product
import com.groupal.ecommerce.shared.CompanionLogger
import org.springframework.stereotype.Component

@Component
class CreateProductUseCase(
    private val createProductRepositoryPort: CreateProductRepositoryPort,
): CreateProductInPort {

    override fun execute(product: Product): Product = product
        .log { info("Inicio de proceso de creación de producto con body request : $it") }
        .let { createProductRepositoryPort.createProduct(product) }
        .log { info("Fin de proceso de creación de producto con response : $it") }

    companion object: CompanionLogger()
}