package com.groupal.ecommerce.shared.persistance.exception

import com.groupal.ecommerce.shared.config.exception.GenericException


open class RestClientGenericException(
        errorCode: Int,
        message: String,
        cause: Throwable? = null
) : GenericException(
        errorCode,
        message,
        cause
)
