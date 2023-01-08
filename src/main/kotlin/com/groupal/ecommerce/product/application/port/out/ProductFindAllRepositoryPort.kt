package com.groupal.ecommerce.product.application.port.out

import com.groupal.ecommerce.product.domain.Product

interface ProductFindAllRepositoryPort {
    fun findAllProducts(): List<Product>
}