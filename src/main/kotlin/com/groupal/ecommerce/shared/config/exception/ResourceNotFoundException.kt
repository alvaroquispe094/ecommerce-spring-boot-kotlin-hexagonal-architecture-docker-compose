package com.groupal.ecommerce.shared.config.exception

open class ResourceNotFoundException(
        errorCode: Int,
        message: String,
        cause: Throwable? = null
) : GenericException(
        errorCode,
        message,
        cause
)
