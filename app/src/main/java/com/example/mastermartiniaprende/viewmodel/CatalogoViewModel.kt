package com.example.mastermartiniaprende.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.mastermartiniaprende.model.Curso
import com.example.mastermartiniaprende.repository.CursoRepository

class CatalogoViewModel : ViewModel() {

    var cursos = mutableStateOf(
        CursoRepository.obtenerCursos()
    )
        private set

    fun obtenerCursoPorId(id: Int): Curso? {
        return cursos.value.find { it.id == id }
    }
}