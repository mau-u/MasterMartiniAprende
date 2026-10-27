package com.example.mastermartiniaprende.repository

import com.example.mastermartiniaprende.model.Leccion

object LeccionRepository {

    private val lecciones = listOf(

        // CLASE 1
        Leccion(
            id = 101,
            cursoId = 1,
            numero = 1,
            titulo = "Ingredientes y utensilios",
            descripcion = "Conoce los materiales necesarios " +
                    "para preparar una cobertura de chocolate brillante.",
            duracionMinutos = 10,
            materiales = listOf(
                "Chocolate de cobertura",
                "Crema de leche",
                "Espátula",
                "Bowl resistente al calor",
                "Balanza de cocina"
            ),
            pasos = listOf(
                "Reúne todos los ingredientes y utensilios.",
                "Pesa los ingredientes según la receta.",
                "Prepara una superficie limpia de trabajo.",
                "Organiza los materiales antes de comenzar."
            )
        ),

        // CLASE 2
        Leccion(
            id = 102,
            cursoId = 1,
            numero = 2,
            titulo = "Preparación de la base",
            descripcion = "Aprende a preparar y acondicionar " +
                    "la base que recibirá la cobertura.",
            duracionMinutos = 15,
            materiales = listOf(
                "Base de bizcocho o pastel",
                "Cuchillo de pastelería",
                "Espátula",
                "Rejilla para enfriar"
            ),
            pasos = listOf(
                "Comprueba que la base esté completamente fría.",
                "Nivela la superficie si es necesario.",
                "Retira las migas sueltas.",
                "Coloca la preparación sobre una rejilla estable."
            )
        ),

        // CLASE 3
        Leccion(
            id = 103,
            cursoId = 1,
            numero = 3,
            titulo = "Preparación de la cobertura",
            descripcion = "Aprende a preparar una cobertura " +
                    "uniforme para conseguir un acabado atractivo.",
            duracionMinutos = 20,
            materiales = listOf(
                "Chocolate de cobertura",
                "Crema de leche",
                "Bowl",
                "Espátula",
                "Termómetro de cocina"
            ),
            pasos = listOf(
                "Pica el chocolate en trozos pequeños.",
                "Calienta la crema de acuerdo con la receta elegida.",
                "Vierte la crema caliente sobre el chocolate.",
                "Mezcla suavemente hasta obtener una preparación homogénea.",
                "Deja que la cobertura alcance la temperatura de aplicación."
            )
        ),

        // CLASE 4
        Leccion(
            id = 104,
            cursoId = 1,
            numero = 4,
            titulo = "Aplicación del baño brillante",
            descripcion = "Descubre cómo cubrir la preparación " +
                    "para lograr una superficie uniforme y brillante.",
            duracionMinutos = 15,
            materiales = listOf(
                "Cobertura preparada",
                "Pastel frío",
                "Rejilla",
                "Espátula de pastelería",
                "Bandeja recolectora"
            ),
            pasos = listOf(
                "Comprueba que la cobertura tenga la consistencia adecuada.",
                "Coloca el pastel sobre la rejilla.",
                "Vierte la cobertura de forma continua y controlada.",
                "Evita manipular demasiado la superficie.",
                "Deja que el exceso escurra antes de trasladar el pastel."
            )
        ),

        // CLASE 5
        Leccion(
            id = 105,
            cursoId = 1,
            numero = 5,
            titulo = "Presentación del resultado final",
            descripcion = "Aprende a revisar los acabados " +
                    "y preparar tu producto para la presentación final.",
            duracionMinutos = 10,
            materiales = listOf(
                "Pastel terminado",
                "Plato de presentación",
                "Espátula pequeña",
                "Elementos decorativos aptos para alimentos"
            ),
            pasos = listOf(
                "Revisa que la cobertura tenga un acabado uniforme.",
                "Limpia los bordes del plato de presentación.",
                "Agrega decoración si corresponde.",
                "Comprueba que el resultado esté listo para presentar.",
                "Prepara tu producto para fotografiarlo como evidencia del curso."
            )
        )
    )

    // Obtener las lecciones correspondientes a un curso
    fun obtenerLeccionesPorCurso(cursoId: Int): List<Leccion> {
        return lecciones.filter { leccion ->
            leccion.cursoId == cursoId
        }.sortedBy { it.numero }
    }

    // Buscar una lección específica
    fun obtenerLeccionPorId(id: Int): Leccion? {
        return lecciones.find { leccion ->
            leccion.id == id
        }
    }
}