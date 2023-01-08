package com.groupal.ecommerce.product.adapter.persistance.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.groupal.ecommerce.product.domain.Product
import javax.persistence.*

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
@Entity
@Table(name="Product")
data class ProductEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long?,
    val code: String,
    val name: String,
    val description: String,
    val price: Double,
    val image: String,
    val stock: Int,
    val categoryId: Int,
){

    fun toProductDomain(): Product {
        return Product(
            id = id,
            code = code,
            name = name,
            description = description,
            price = price,
            image = image,
            stock = stock,
            categoryId = categoryId,
        )
    }
}
