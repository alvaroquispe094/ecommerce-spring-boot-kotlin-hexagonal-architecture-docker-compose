package com.groupal.ecommerce.product.application.usecase

import com.groupal.ecommerce.product.application.port.`in`.FindProductByIdInPort
import com.groupal.ecommerce.product.application.port.out.ProductByIdRepositoryPort
import com.groupal.ecommerce.product.domain.Product
import com.groupal.ecommerce.shared.CompanionLogger
import org.springframework.stereotype.Component

@Component
class GetProductByIdUseCase(
    private val productByIdRepositoryPort: ProductByIdRepositoryPort,
): FindProductByIdInPort {

    override fun execute(id: Long): Product = id
        .log {  info("Inicio de proceso de busqueda de producto con id = {}", it) }
        .let { productByIdRepositoryPort.findProductById(it) }
        .log { info("Fin de proceso de busqueda de producto - response : $it") }

    companion object: CompanionLogger()
}