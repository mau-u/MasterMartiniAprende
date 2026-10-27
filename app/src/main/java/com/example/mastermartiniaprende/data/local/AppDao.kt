package com.example.mastermartiniaprende.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AppDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertarUsuario(usuario: UsuarioEntity): Long

    @Query("SELECT * FROM usuarios WHERE email = :email LIMIT 1")
    suspend fun obtenerUsuario(email: String): UsuarioEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertarInscripcion(inscripcion: InscripcionEntity)

    @Query("SELECT * FROM inscripciones WHERE emailUsuario = :email")
    suspend fun obtenerInscripciones(email: String): List<InscripcionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun guardarClaseCompletada(clase: ClaseCompletadaEntity)

    @Query("SELECT * FROM clases_completadas WHERE emailUsuario = :email")
    suspend fun obtenerClasesCompletadas(email: String): List<ClaseCompletadaEntity>

    @Query("DELETE FROM clases_completadas WHERE emailUsuario = :email AND cursoId = :cursoId AND numeroClase = :numeroClase")
    suspend fun eliminarClaseCompletada(email: String, cursoId: Int, numeroClase: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun guardarFotoResultado(foto: FotoResultadoEntity)

    @Query("SELECT * FROM fotos_resultado WHERE emailUsuario = :email")
    suspend fun obtenerFotosResultado(email: String): List<FotoResultadoEntity>
}