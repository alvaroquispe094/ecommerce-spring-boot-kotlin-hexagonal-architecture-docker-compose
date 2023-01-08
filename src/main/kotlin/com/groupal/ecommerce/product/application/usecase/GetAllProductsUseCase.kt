package com.groupal.ecommerce.product.application.usecase

import com.groupal.ecommerce.product.application.port.`in`.FindAllProductsInPort
import com.groupal.ecommerce.product.application.port.out.ProductFindAllRepositoryPort
import com.groupal.ecommerce.product.domain.Product
import com.groupal.ecommerce.product.application.usecase.CreateProductUseCase.Companion.log
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class GetAllProductsUseCase(
    private val productFindAllRepositoryPort: ProductFindAllRepositoryPort,
): FindAllProductsInPort {

    private val logger = LoggerFactory.getLogger(GetAllProductsUseCase::class.java)

    override fun execute(): List<Product> = run {
        logger.info("Inicio de proceso de busqueda de productos")
            .let { productFindAllRepositoryPort.findAllProducts() }
            .log { info("Fin de proceso de busqueda de productos") }

    }
}