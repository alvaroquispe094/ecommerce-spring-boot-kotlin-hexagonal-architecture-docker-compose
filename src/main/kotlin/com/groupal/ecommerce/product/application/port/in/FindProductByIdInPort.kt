package com.groupal.ecommerce.product.application.port.`in`

import com.groupal.ecommerce.product.domain.Product

interface FindProductByIdInPort {
    fun execute(id: Long): Product
}