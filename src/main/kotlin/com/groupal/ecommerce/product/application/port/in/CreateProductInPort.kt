package com.groupal.ecommerce.product.application.port.`in`

import com.groupal.ecommerce.product.domain.Product

interface CreateProductInPort {
    fun execute(product: Product): Product
}