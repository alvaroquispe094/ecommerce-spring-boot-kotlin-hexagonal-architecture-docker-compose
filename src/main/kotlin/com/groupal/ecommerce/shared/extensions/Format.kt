package com.groupal.ecommerce.shared.extensions

fun String.toSnakeCase(): String =
        this.map {
            if (it.isUpperCase()) "_" + it.lowercaseChar()
            else it.toString()
        }.joinToString(separator = "")
