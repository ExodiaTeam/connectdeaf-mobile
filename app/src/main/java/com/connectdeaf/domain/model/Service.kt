package com.connectdeaf.domain.model

data class Service(
    val id: Int? = null, // ID nulo para novos serviços
    val name: String,
    val description: String,
    val value: Double,
    val categories: List<String>, // Ou um tipo de dados enum para categorias
    val imageUrl: String
)
