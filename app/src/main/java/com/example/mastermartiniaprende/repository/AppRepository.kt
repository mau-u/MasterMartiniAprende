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

    // Registrar una cuenta nueva
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

    // Iniciar sesión comprobando la contraseña
    suspend fun iniciarSesion(
        email: String,
        password: String
    ): Usuario? {
        val emailNormalizado = email.trim().lowercase()

        val usuarioEntity =
            appDao.obtenerUsuario(emailNormalizado)
                ?: return null

        val passwordValida = withContext(Dispatchers.Default) {
            PasswordHasher.verificar(
                password = password,
                salt = usuarioEntity.passwordSalt,
                hashGuardado = usuarioEntity.passwordHash
            )
        }

        return if (passwordValida) {
            Usuario(
                nombre = usuarioEntity.nombre,
                email = usuarioEntity.email,
                tipoNegocio = usuarioEntity.tipoNegocio
            )
        } else {
            null
        }
    }

    // Recuperar una cuenta existente para restaurar la sesión.
    // No vuelve a solicitar la contraseña, porque la sesión
    // ya fue autenticada anteriormente.
    suspend fun obtenerUsuarioPorEmail(
        email: String
    ): Usuario? {
        val emailNormalizado = email.trim().lowercase()

        val usuarioEntity =
            appDao.obtenerUsuario(emailNormalizado)
                ?: return null

        return Usuario(
            nombre = usuarioEntity.nombre,
            email = usuarioEntity.email,
            tipoNegocio = usuarioEntity.tipoNegocio
        )
    }

    // Recuperar cursos, clases completadas y fotografías del usuario
    suspend fun cargarDatosCursos(
        email: String
    ): DatosUsuarioCursos {
        val emailNormalizado = email.trim().lowercase()

        val inscripciones =
            appDao.obtenerInscripciones(emailNormalizado)

        val clases =
            appDao.obtenerClasesCompletadas(emailNormalizado)

        val fotos =
            appDao.obtenerFotosResultado(emailNormalizado)

        return DatosUsuarioCursos(
            cursosInscritos = inscripciones
                .map { it.cursoId }
                .toSet(),

            clasesCompletadas = clases
                .groupBy { it.cursoId }
                .mapValues { (_, registros) ->
                    registros
                        .map { it.numeroClase }
                        .toSet()
                },

            fotosResultado = fotos.associate {
                it.cursoId to it.rutaLocal
            }
        )
    }

    // Inscribir al usuario en un curso
    suspend fun inscribirCurso(
        email: String,
        cursoId: Int
    ) {
        appDao.insertarInscripcion(
            InscripcionEntity(
                emailUsuario = email.trim().lowercase(),
                cursoId = cursoId
            )
        )
    }

    // Guardar o eliminar el progreso de una clase
    suspend fun guardarProgresoClase(
        email: String,
        cursoId: Int,
        numeroClase: Int,
        completada: Boolean
    ) {
        val emailNormalizado = email.trim().lowercase()

        if (completada) {
            appDao.guardarClaseCompletada(
                ClaseCompletadaEntity(
                    emailUsuario = emailNormalizado,
                    cursoId = cursoId,
                    numeroClase = numeroClase
                )
            )
        } else {
            appDao.eliminarClaseCompletada(
                emailNormalizado,
                cursoId,
                numeroClase
            )
        }
    }

    // Guardar la ruta de la fotografía final
    suspend fun guardarFotoResultado(
        email: String,
        cursoId: Int,
        rutaLocal: String
    ) {
        appDao.guardarFotoResultado(
            FotoResultadoEntity(
                emailUsuario = email.trim().lowercase(),
                cursoId = cursoId,
                rutaLocal = rutaLocal
            )
        )
    }
}