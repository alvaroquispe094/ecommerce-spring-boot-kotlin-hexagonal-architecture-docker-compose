package com.groupal.ecommerce.product.application.port.out

import com.groupal.ecommerce.product.domain.Product

interface CreateProductRepositoryPort {
    fun createProduct(product: Product): Product
}