package com.groupal.ecommerce.product.adapter.persistance


import com.groupal.ecommerce.product.adapter.persistance.mapper.ProductMapper
import com.groupal.ecommerce.product.adapter.persistance.repository.SpringDataProductRepository
import com.groupal.ecommerce.product.application.port.out.CreateProductRepositoryPort
import com.groupal.ecommerce.product.application.port.out.ProductByIdRepositoryPort
import com.groupal.ecommerce.product.application.port.out.ProductDeleteRepositoryPort
import com.groupal.ecommerce.product.application.port.out.ProductFindAllRepositoryPort
import com.groupal.ecommerce.product.domain.Product
import com.groupal.ecommerce.shared.CompanionLogger
import com.groupal.ecommerce.shared.config.MessageError
import com.groupal.ecommerce.shared.config.exception.BadArgumentException
import com.groupal.ecommerce.shared.config.exception.DaoException
import com.groupal.ecommerce.shared.config.exception.ResourceNotFoundException
import org.springframework.stereotype.Repository

@Repository
class ProductRepositoryAdapter(
    val repository: SpringDataProductRepository
): ProductFindAllRepositoryPort, ProductByIdRepositoryPort, CreateProductRepositoryPort, ProductDeleteRepositoryPort {

    override fun findAllProducts(): List<Product> {
        return repository.findAll().map { it.toProductDomain() }
    }

    override fun findProductById(productId: Long) = try {
        productId
            .log { info("ProductWebService Request - id = {}", productId) }
            .let { repository.findById(it) }
            .orElseThrow { ResourceNotFoundException(MessageError.RESOURCE_NOT_FOUND.errorCode, "No se encontro el producto con id $productId") }
            .toProductDomain()
            .log { info("ProductWebService Response: $it") }
    }catch (e: ResourceNotFoundException){
        logger.error("Error al acceder al recurso con id : $productId")
        throw ResourceNotFoundException(MessageError.RESOURCE_NOT_FOUND.errorCode, "No se encontro el producto con id = $productId")
    }catch (e: IllegalArgumentException){
        logger.error("Error al acceder al recurso, el id es null")
        throw DaoException(MessageError.BAD_REQUEST.errorCode, "No se pudo encontrar el producto debido a que el id es null")
    }

    override fun createProduct(product: Product) = try {
        product
            .log { info("ProductWebService Request: {}", product) }
            .let { ProductMapper.toProductEntity(it) }
            .let { repository.save(it) }
            .toProductDomain()
            .log {  info("ProductWebService Response: $it")}
    } catch (e: IllegalArgumentException){
        logger.error("Error al grabar al recurso, la entidad es null")
        throw DaoException(MessageError.BAD_REQUEST.errorCode, "No se pudo grabar el producto debido a que la entidad dominio es null")
    }

    override fun deleteProduct(productId: Long) = try {
        productId
            .log { info("ProductWebService delete Request - id = {}", productId) }
            .let { repository.deleteById(it) }
            .log {  info("ProductWebService delete Response")}
    }catch (e: ResourceNotFoundException){
        logger.error("Error al acceder al recurso con id : $productId")
        throw ResourceNotFoundException(MessageError.RESOURCE_NOT_FOUND.errorCode, "No se encontro el producto con id = $productId")
    }catch (e: Exception){
        logger.error("Error al acceder al recurso, el id es null")
        throw BadArgumentException(MessageError.ILLEGAL_ARGUMENT.errorCode, "No se pudo encontrar el producto debido a que el id no existe")
    }

    companion object: CompanionLogger()
}