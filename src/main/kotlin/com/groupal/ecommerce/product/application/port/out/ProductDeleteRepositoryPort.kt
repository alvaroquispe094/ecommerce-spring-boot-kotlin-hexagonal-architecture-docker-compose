package com.groupal.ecommerce.product.application.port.out

interface ProductDeleteRepositoryPort {
    fun deleteProduct(productId: Long)
}