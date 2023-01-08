package com.groupal.ecommerce.shared.config.exception

abstract class GenericException(
        val errorCode: Int,
        message: String,
        cause: Throwable? = null
) : RuntimeException(
        message,
        cause
)
