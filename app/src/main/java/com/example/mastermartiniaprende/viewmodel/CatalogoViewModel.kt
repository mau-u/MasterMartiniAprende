package com.example.mastermartiniaprende.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.mastermartiniaprende.model.Curso
import com.example.mastermartiniaprende.repository.CursoRepository

class CatalogoViewModel : ViewModel() {

    // Lista de cursos disponibles
    var cursos = mutableStateOf(
        CursoRepository.obtenerCursos()
    )
        private set

    // Guarda las clases completadas, separadas por curso
    private val clasesCompletadas =
        mutableStateOf<Map<Int, Set<Int>>>(emptyMap())

    // Fotografías finales guardadas por curso
    private val fotosResultado =
        mutableStateOf<Map<Int, String>>(emptyMap())

    // Guarda los identificadores de los cursos inscritos
    private val cursosInscritos =
        mutableStateOf<Set<Int>>(emptySet())


    // Buscar un curso por su identificador
    fun obtenerCursoPorId(id: Int): Curso? {
        return cursos.value.find { it.id == id }
    }


    // Comprobar si una clase está completada
    fun estaClaseCompletada(
        cursoId: Int,
        numeroClase: Int
    ): Boolean {
        return clasesCompletadas.value[cursoId]
            ?.contains(numeroClase) == true
    }


    // Marcar o desmarcar una clase
    fun alternarClaseCompletada(
        cursoId: Int,
        numeroClase: Int
    ) {
        val actuales: Set<Int> =
            clasesCompletadas.value[cursoId].orEmpty()

        val actualizadas = if (numeroClase in actuales) {
            actuales - numeroClase
        } else {
            actuales + numeroClase
        }

        clasesCompletadas.value =
            clasesCompletadas.value + (cursoId to actualizadas)
    }


    // Calcular el porcentaje de avance
    fun progresoCurso(curso: Curso): Int {

        val totalClases = curso.cantidadClases

        if (totalClases <= 0) {
            return 0
        }

        val completadas =
            clasesCompletadas.value[curso.id].orEmpty()

        val cantidadCompletadas = completadas.count {
            it in 1..totalClases
        }

        return (cantidadCompletadas * 100) / totalClases
    }


    // INSCRIPCIONES A CURSOS

    // Inscribir al usuario en un curso
    fun inscribirCurso(cursoId: Int) {
        cursosInscritos.value =
            cursosInscritos.value + cursoId
    }


    // Comprobar si el usuario ya está inscrito
    fun estaInscrito(cursoId: Int): Boolean {
        return cursoId in cursosInscritos.value
    }


    // Obtener la lista de cursos inscritos
    fun obtenerMisCursos(): List<Curso> {
        return cursos.value.filter { curso ->
            curso.id in cursosInscritos.value
        }
    }
    // Guardar la ruta de la fotografía final
    fun guardarFotoResultado(
        cursoId: Int,
        rutaFoto: String
    ) {
        fotosResultado.value =
            fotosResultado.value + (cursoId to rutaFoto)
    }

    // Obtener la fotografía final de un curso
    fun obtenerFotoResultado(cursoId: Int): String? {
        return fotosResultado.value[cursoId]
    }

    // Obtener los cursos que ya tienen una fotografía final
    fun obtenerCursosConFoto(): List<Curso> {
        return cursos.value.filter { curso ->
            fotosResultado.value.containsKey(curso.id)
        }
    }
}