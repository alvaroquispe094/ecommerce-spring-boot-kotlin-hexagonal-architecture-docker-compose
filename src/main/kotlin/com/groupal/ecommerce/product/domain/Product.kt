package com.groupal.ecommerce.product.domain

data class Product(
    val id: Long?,
    val code: String,
    val name: String,
    val description: String,
    val price: Double,
    val image: String,
    val stock: Int,
    val categoryId: Int,
)