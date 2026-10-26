package com.example.mastermartiniaprende.model

data class Curso(
    val id: Int,
    val titulo: String,
    val categoria: String,
    val descripcion: String,
    val nivel: String,
    val cantidadClases: Int
)