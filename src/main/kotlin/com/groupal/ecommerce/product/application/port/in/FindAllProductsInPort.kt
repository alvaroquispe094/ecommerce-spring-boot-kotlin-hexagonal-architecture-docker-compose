package com.groupal.ecommerce.product.application.port.`in`

import com.groupal.ecommerce.product.domain.Product

interface FindAllProductsInPort {
    fun execute(): List<Product>
}