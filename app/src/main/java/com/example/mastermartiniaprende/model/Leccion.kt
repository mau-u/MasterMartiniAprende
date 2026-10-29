package com.example.mastermartiniaprende.model

data class Leccion(
    val id: Int,
    val cursoId: Int,
    val numero: Int,
    val titulo: String,
    val descripcion: String,
    val duracionMinutos: Int,
    val materiales: List<String>,
    val pasos: List<String>,
    val videoResourceId: Int? = null,
    val imagenResourceId: Int? = null
)
