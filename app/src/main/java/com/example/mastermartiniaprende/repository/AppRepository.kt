package com.example.mastermartiniaprende.repository

import com.example.mastermartiniaprende.data.local.AppDao
import com.example.mastermartiniaprende.data.local.ClaseCompletadaEntity
import com.example.mastermartiniaprende.data.local.FotoResultadoEntity
import com.example.mastermartiniaprende.data.local.InscripcionEntity
import com.example.mastermartiniaprende.data.local.UsuarioEntity
import com.example.mastermartiniaprende.model.Usuario
import com.example.mastermartiniaprende.security.PasswordHasher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

data class DatosUsuarioCursos(
    val cursosInscritos: Set<Int>,
    val clasesCompletadas: Map<Int, Set<Int>>,
    val fotosResultado: Map<Int, String>
)

class AppRepository(private val appDao: AppDao) {
    suspend fun registrarUsuario(
        nombre: String,
        email: String,
        password: String,
        tipoNegocio: String
    ): Boolean {
        val emailNormalizado = email.trim().lowercase()
        val salt = PasswordHasher.crearSalt()
        val hash = withContext(Dispatchers.Default) {
            PasswordHasher.generarHash(password, salt)
        }

        val resultado = appDao.insertarUsuario(
            UsuarioEntity(
                email = emailNormalizado,
                nombre = nombre.trim(),
                tipoNegocio = tipoNegocio.trim(),
                passwordHash = hash,
                passwordSalt = salt
            )
        )
        return resultado != -1L
    }

    suspend fun iniciarSesion(email: String, password: String): Usuario? {
        val emailNormalizado = email.trim().lowercase()
        val usuario = appDao.obtenerUsuario(emailNormalizado) ?: return null
        val passwordValida = withContext(Dispatchers.Default) {
            PasswordHasher.verificar(
                password = password,
                salt = usuario.passwordSalt,
                hashGuardado = usuario.passwordHash
            )
        }

        return if (passwordValida) {
            Usuario(
                nombre = usuario.nombre,
                email = usuario.email,
                tipoNegocio = usuario.tipoNegocio
            )
        } else {
            null
        }
    }

    suspend fun cargarDatosCursos(email: String): DatosUsuarioCursos {
        val inscripciones = appDao.obtenerInscripciones(email)
        val clases = appDao.obtenerClasesCompletadas(email)
        val fotos = appDao.obtenerFotosResultado(email)

        return DatosUsuarioCursos(
            cursosInscritos = inscripciones.map { it.cursoId }.toSet(),
            clasesCompletadas = clases
                .groupBy { it.cursoId }
                .mapValues { (_, registros) ->
                    registros.map { it.numeroClase }.toSet()
                },
            fotosResultado = fotos.associate { it.cursoId to it.rutaLocal }
        )
    }

    suspend fun inscribirCurso(email: String, cursoId: Int) {
        appDao.insertarInscripcion(
            InscripcionEntity(emailUsuario = email, cursoId = cursoId)
        )
    }

    suspend fun guardarProgresoClase(
        email: String,
        cursoId: Int,
        numeroClase: Int,
        completada: Boolean
    ) {
        if (completada) {
            appDao.guardarClaseCompletada(
                ClaseCompletadaEntity(
                    emailUsuario = email,
                    cursoId = cursoId,
                    numeroClase = numeroClase
                )
            )
        } else {
            appDao.eliminarClaseCompletada(email, cursoId, numeroClase)
        }
    }

    suspend fun guardarFotoResultado(email: String, cursoId: Int, rutaLocal: String) {
        appDao.guardarFotoResultado(
            FotoResultadoEntity(
                emailUsuario = email,
                cursoId = cursoId,
                rutaLocal = rutaLocal
            )
        )
    }
}