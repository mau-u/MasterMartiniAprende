package com.example.mastermartiniaprende.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.mastermartiniaprende.model.Leccion
import com.example.mastermartiniaprende.repository.LeccionRepository

class LeccionViewModel : ViewModel() {

    // Lista de lecciones del curso seleccionado
    var lecciones = mutableStateOf<List<Leccion>>(emptyList())
        private set

    // Lección que el usuario está consultando
    var leccionSeleccionada = mutableStateOf<Leccion?>(null)
        private set

    // Cargar las lecciones de un curso
    fun cargarLecciones(cursoId: Int) {
        lecciones.value =
            LeccionRepository.obtenerLeccionesPorCurso(cursoId)
    }

    // Buscar una lección por su identificador
    fun obtenerLeccionPorId(id: Int): Leccion? {
        return LeccionRepository.obtenerLeccionPorId(id)
    }

    // Seleccionar una lección para mostrar su contenido
    fun seleccionarLeccion(id: Int) {
        leccionSeleccionada.value =
            LeccionRepository.obtenerLeccionPorId(id)
    }
}