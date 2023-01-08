package com.groupal.ecommerce.product.application.usecase

import com.groupal.ecommerce.product.application.port.`in`.DeleteProductInPort
import com.groupal.ecommerce.product.application.port.out.ProductDeleteRepositoryPort
import com.groupal.ecommerce.shared.CompanionLogger
import org.springframework.stereotype.Component

@Component
class DeleteProductUseCase(
    private val productDeleteRepositoryPort: ProductDeleteRepositoryPort,
): DeleteProductInPort {

    override fun execute(idProduct: Long) =
        idProduct
            .log {  info("Inicio de proceso de busqueda de producto con id = {}", it) }
            .let { productDeleteRepositoryPort.deleteProduct(it) }
            .log { info("Fin de proceso de busqueda de producto - response : $it") }

    companion object: CompanionLogger()
}