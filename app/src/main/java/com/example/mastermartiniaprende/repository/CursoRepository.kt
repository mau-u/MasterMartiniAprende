package com.example.mastermartiniaprende.repository

import com.example.mastermartiniaprende.model.Curso

object CursoRepository {

    fun obtenerCursos(): List<Curso> {
        return listOf(
            Curso(
                id = 1,
                titulo = "Chocolate brillante",
                categoria = "Chocolatería",
                descripcion = "Aprende las técnicas básicas para trabajar con chocolate.",
                nivel = "Inicial",
                cantidadClases = 5
            ),
            Curso(
                id = 2,
                titulo = "Pastelería de vitrina",
                categoria = "Pastelería",
                descripcion = "Descubre cómo preparar y presentar productos de pastelería.",
                nivel = "Inicial",
                cantidadClases = 4
            ),
            Curso(
                id = 3,
                titulo = "Masas y bollería",
                categoria = "Panadería",
                descripcion = "Aprende los fundamentos de las masas y la bollería.",
                nivel = "Inicial",
                cantidadClases = 6
            )
        )
    }
}