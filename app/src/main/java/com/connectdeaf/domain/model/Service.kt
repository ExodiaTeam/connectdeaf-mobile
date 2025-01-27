package com.connectdeaf.domain.model

data class Service(
    val id: String? = null,
    val name: String,
    val description: String,
    val value: Double,
    val categories: List<String>,
    val imageUrl: String? = null
)