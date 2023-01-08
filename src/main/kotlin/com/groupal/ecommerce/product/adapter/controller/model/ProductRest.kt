package com.groupal.ecommerce.product.adapter.controller.model

import com.groupal.ecommerce.product.domain.Product
import javax.validation.Valid
import javax.validation.constraints.NotBlank

data class ProductRest(
    @get:Valid val id: Long?,
    @get:Valid val code: String,
    @get:NotBlank val name: String,
    @get:Valid val description: String,
    @get:Valid val price: Double,
    @get:Valid val image: String,
    @get:Valid val stock: Int,
    @get:Valid val categoryId: Int,
) {
    fun toDomain() =
        Product(
            id = id,
            code = code,
            name = name,
            description = description,
            price = price,
            image = image,
            stock = stock,
            categoryId = categoryId,
        )

    companion object {
        infix fun from(product: Product): ProductRest {
            return ProductRest(
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
}