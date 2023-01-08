package com.groupal.ecommerce.product.adapter.persistance.mapper

import com.groupal.ecommerce.product.adapter.persistance.model.ProductEntity
import com.groupal.ecommerce.product.domain.Product

object ProductMapper {

    fun toProductEntity(product: Product): ProductEntity {
        return ProductEntity(
            id = product.id,
            code = product.code,
            name = product.name,
            description = product.description,
            price = product.price,
            image = product.image,
            stock = product.stock,
            categoryId = product.categoryId,
        )
    }
}