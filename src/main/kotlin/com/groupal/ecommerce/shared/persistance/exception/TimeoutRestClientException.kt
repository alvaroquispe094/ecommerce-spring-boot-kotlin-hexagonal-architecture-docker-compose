package com.groupal.ecommerce.shared.persistance.exception

class TimeoutRestClientException(
        errorCode: Int,
        message: String,
        cause: Throwable? = null
) : RestClientGenericException(
        errorCode,
        message,
        cause
)


