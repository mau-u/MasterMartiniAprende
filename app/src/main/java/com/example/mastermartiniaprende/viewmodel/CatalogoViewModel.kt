package com.example.mastermartiniaprende.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mastermartiniaprende.model.Curso
import com.example.mastermartiniaprende.repository.AppRepository
import com.example.mastermartiniaprende.repository.CursoRepository
import kotlinx.coroutines.launch

class CatalogoViewModel(private val repository: AppRepository) : ViewModel() {
    var cursos = mutableStateOf(CursoRepository.obtenerCursos())
        private set

    private val emailUsuario = mutableStateOf<String?>(null)
    private val clasesCompletadas = mutableStateOf<Map<Int, Set<Int>>>(emptyMap())
    private val fotosResultado = mutableStateOf<Map<Int, String>>(emptyMap())
    private val cursosInscritos = mutableStateOf<Set<Int>>(emptySet())
    var mensaje = mutableStateOf("")
        private set

    fun cargarUsuario(email: String, onListo: () -> Unit) {
        val emailNormalizado = email.trim().lowercase()
        emailUsuario.value = emailNormalizado
        cursosInscritos.value = emptySet()
        clasesCompletadas.value = emptyMap()
        fotosResultado.value = emptyMap()

        viewModelScope.launch {
            try {
                val datos = repository.cargarDatosCursos(emailNormalizado)
                cursosInscritos.value = datos.cursosInscritos
                clasesCompletadas.value = datos.clasesCompletadas
                fotosResultado.value = datos.fotosResultado
                mensaje.value = ""
            } catch (error: Exception) {
                mensaje.value = "No se pudieron cargar tus cursos guardados."
            } finally {
                onListo()
            }
        }
    }

    fun cerrarSesion() {
        emailUsuario.value = null
        cursosInscritos.value = emptySet()
        clasesCompletadas.value = emptyMap()
        fotosResultado.value = emptyMap()
        mensaje.value = ""
    }

    fun obtenerCursoPorId(id: Int): Curso? = cursos.value.find { it.id == id }

    fun estaClaseCompletada(cursoId: Int, numeroClase: Int): Boolean =
        clasesCompletadas.value[cursoId]?.contains(numeroClase) == true

    fun alternarClaseCompletada(cursoId: Int, numeroClase: Int) {
        val email = emailUsuario.value ?: return
        val actuales = clasesCompletadas.value[cursoId].orEmpty()
        val quedaraCompletada = numeroClase !in actuales
        val actualizadas = if (quedaraCompletada) {
            actuales + numeroClase
        } else {
            actuales - numeroClase
        }
        clasesCompletadas.value = clasesCompletadas.value + (cursoId to actualizadas)

        viewModelScope.launch {
            try {
                repository.guardarProgresoClase(email, cursoId, numeroClase, quedaraCompletada)
            } catch (error: Exception) {
                mensaje.value = "No se pudo guardar el avance de la clase."
            }
        }
    }

    fun progresoCurso(curso: Curso): Int {
        val totalClases = curso.cantidadClases
        if (totalClases <= 0) return 0
        val completadas = clasesCompletadas.value[curso.id].orEmpty()
        val cantidadCompletadas = completadas.count { it in 1..totalClases }
        return cantidadCompletadas * 100 / totalClases
    }

    fun inscribirCurso(cursoId: Int) {
        val email = emailUsuario.value ?: return
        cursosInscritos.value = cursosInscritos.value + cursoId
        viewModelScope.launch {
            try {
                repository.inscribirCurso(email, cursoId)
            } catch (error: Exception) {
                mensaje.value = "No se pudo guardar la inscripción al curso."
            }
        }
    }

    fun estaInscrito(cursoId: Int): Boolean = cursoId in cursosInscritos.value

    fun obtenerMisCursos(): List<Curso> = cursos.value.filter { it.id in cursosInscritos.value }

    fun guardarFotoResultado(cursoId: Int, rutaFoto: String) {
        val email = emailUsuario.value ?: return
        fotosResultado.value = fotosResultado.value + (cursoId to rutaFoto)
        viewModelScope.launch {
            try {
                repository.guardarFotoResultado(email, cursoId, rutaFoto)
            } catch (error: Exception) {
                mensaje.value = "La foto se guardó, pero no su referencia."
            }
        }
    }

    fun obtenerFotoResultado(cursoId: Int): String? = fotosResultado.value[cursoId]

    fun obtenerCursosConFoto(): List<Curso> =
        cursos.value.filter { it.id in fotosResultado.value.keys }
}