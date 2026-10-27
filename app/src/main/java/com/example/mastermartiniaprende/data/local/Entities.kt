package com.example.mastermartiniaprende.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class UsuarioEntity(
    @PrimaryKey val email: String,
    val nombre: String,
    val tipoNegocio: String,
    val passwordHash: String,
    val passwordSalt: String
)

@Entity(tableName = "inscripciones", primaryKeys = ["emailUsuario", "cursoId"])
data class InscripcionEntity(
    val emailUsuario: String,
    val cursoId: Int
)

@Entity(
    tableName = "clases_completadas",
    primaryKeys = ["emailUsuario", "cursoId", "numeroClase"]
)
data class ClaseCompletadaEntity(
    val emailUsuario: String,
    val cursoId: Int,
    val numeroClase: Int
)

@Entity(tableName = "fotos_resultado", primaryKeys = ["emailUsuario", "cursoId"])
data class FotoResultadoEntity(
    val emailUsuario: String,
    val cursoId: Int,
    val rutaLocal: String
)