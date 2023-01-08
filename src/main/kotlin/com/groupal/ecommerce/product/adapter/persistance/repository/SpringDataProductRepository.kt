package com.groupal.ecommerce.product.adapter.persistance.repository

import com.groupal.ecommerce.product.adapter.persistance.model.ProductEntity
import org.springframework.data.repository.CrudRepository

interface SpringDataProductRepository: CrudRepository<ProductEntity, Long>{
}