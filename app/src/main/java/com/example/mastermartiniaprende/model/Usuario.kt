package com.example.mastermartiniaprende.model

data class Usuario (
    val nombre: String,
    val email: String,
    val password: String,
    val tipoNegocio: String = ""
)