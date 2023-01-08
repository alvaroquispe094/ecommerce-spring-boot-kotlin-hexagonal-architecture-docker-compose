package com.groupal.ecommerce.shared.extensions

fun String.trimSQL() = lines()
        .filter(String::isNotBlank)
        .joinToString(separator = " ") { it.trim() }
