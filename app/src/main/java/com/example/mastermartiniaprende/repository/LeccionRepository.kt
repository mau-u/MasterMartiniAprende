package com.example.mastermartiniaprende.repository

import com.example.mastermartiniaprende.R
import com.example.mastermartiniaprende.model.Leccion

object LeccionRepository {
    private val lecciones = listOf(
        Leccion(
            id = 101,
            cursoId = 1,
            numero = 1,
            titulo = "Ingredientes y utensilios",
            descripcion = "Conoce los materiales para preparar una cobertura de chocolate brillante.",
            duracionMinutos = 10,
            materiales = listOf("Chocolate de cobertura", "Crema de leche", "Espátula", "Bowl resistente al calor", "Balanza de cocina"),
            pasos = listOf("Reúne los ingredientes y utensilios.", "Pesa los ingredientes según la receta.", "Prepara una superficie limpia.", "Organiza los materiales antes de comenzar."),
            imagenResourceId = R.drawable.ingredientes_chocolate
        ),
        Leccion(
            id = 102,
            cursoId = 1,
            numero = 2,
            titulo = "Preparación de la base",
            descripcion = "Acondiciona la base que recibirá la cobertura de chocolate.",
            duracionMinutos = 15,
            materiales = listOf("Base de bizcocho o pastel", "Cuchillo de pastelería", "Espátula", "Rejilla para enfriar"),
            pasos = listOf("Comprueba que la base esté completamente fría.", "Nivela la superficie si es necesario.", "Retira las migas sueltas.", "Coloca la preparación sobre una rejilla estable."),
            imagenResourceId = R.drawable.base_chocolate
        ),
        Leccion(
            id = 103,
            cursoId = 1,
            numero = 3,
            titulo = "Preparación de la cobertura",
            descripcion = "Prepara una cobertura uniforme para conseguir un acabado atractivo.",
            duracionMinutos = 20,
            materiales = listOf("Chocolate de cobertura", "Crema de leche", "Bowl", "Espátula", "Termómetro de cocina"),
            pasos = listOf("Pica el chocolate en trozos pequeños.", "Calienta la crema de acuerdo con la receta.", "Vierte la crema caliente sobre el chocolate.", "Mezcla suavemente hasta obtener una preparación homogénea.", "Deja que alcance la temperatura de aplicación."),
            imagenResourceId = R.drawable.ganache_chocolate,
            videoResourceId = R.raw.clase_cobertura_chocolate
        ),
        Leccion(
            id = 104,
            cursoId = 1,
            numero = 4,
            titulo = "Aplicación del baño brillante",
            descripcion = "Cubre la preparación para lograr una superficie uniforme y brillante.",
            duracionMinutos = 15,
            materiales = listOf("Cobertura preparada", "Pastel frío", "Rejilla", "Espátula de pastelería", "Bandeja recolectora"),
            pasos = listOf("Comprueba que la cobertura tenga la consistencia adecuada.", "Coloca el pastel sobre la rejilla.", "Vierte la cobertura de forma continua y controlada.", "Evita manipular demasiado la superficie.", "Deja escurrir el exceso antes de trasladar el pastel."),
            imagenResourceId = R.drawable.cobertura_chocolate
        ),
        Leccion(
            id = 105,
            cursoId = 1,
            numero = 5,
            titulo = "Presentación del resultado final",
            descripcion = "Revisa los acabados y prepara tu producto para la presentación final.",
            duracionMinutos = 10,
            materiales = listOf("Pastel terminado", "Plato de presentación", "Espátula pequeña", "Elementos decorativos aptos para alimentos"),
            pasos = listOf("Revisa que la cobertura tenga un acabado uniforme.", "Limpia los bordes del plato.", "Agrega decoración si corresponde.", "Comprueba que el resultado esté listo para presentar.", "Prepara tu producto para fotografiarlo."),
            imagenResourceId = R.drawable.resultado_chocolate
        ),
        Leccion(
            id = 201,
            cursoId = 2,
            numero = 1,
            titulo = "Organización y mise en place",
            descripcion = "Prepara tu espacio, ingredientes y utensilios antes de comenzar.",
            duracionMinutos = 10,
            materiales = listOf("Balanza", "Bowl", "Ingredientes", "Bandejas"),
            pasos = listOf("Revisa la receta y reúne los materiales.", "Limpia la superficie de trabajo.", "Pesa y organiza los ingredientes."),
            imagenResourceId = R.drawable.pasteleria_mise_en_place
        ),
        Leccion(
            id = 202,
            cursoId = 2,
            numero = 2,
            titulo = "Preparación de un bizcocho base",
            descripcion = "Conoce los pasos generales para preparar una base de pastelería.",
            duracionMinutos = 20,
            materiales = listOf("Harina", "Huevos", "Azúcar", "Molde", "Balanza"),
            pasos = listOf("Prepara los ingredientes según la receta.", "Mezcla respetando el orden indicado.", "Vierte la preparación en el molde.", "Hornea según la receta y deja enfriar."),
            imagenResourceId = R.drawable.pasteleria_bizcocho_base
        ),
        Leccion(
            id = 203,
            cursoId = 2,
            numero = 3,
            titulo = "Relleno y montaje",
            descripcion = "Rellena y monta una preparación de forma ordenada.",
            duracionMinutos = 18,
            materiales = listOf("Bizcocho frío", "Relleno", "Cuchillo", "Espátula"),
            pasos = listOf("Nivela la base si es necesario.", "Divide el bizcocho en capas.", "Distribuye el relleno de manera uniforme.", "Monta las capas y revisa que queden alineadas."),
            imagenResourceId = R.drawable.pasteleria_montaje
        ),
        Leccion(
            id = 204,
            cursoId = 2,
            numero = 4,
            titulo = "Terminaciones y presentación",
            descripcion = "Prepara el producto para exhibirlo en una vitrina.",
            duracionMinutos = 15,
            materiales = listOf("Producto montado", "Espátula", "Decoración", "Envase"),
            pasos = listOf("Alisa o termina la superficie.", "Agrega la decoración elegida.", "Limpia los bordes y prepara la presentación.", "Revisa el producto antes de llevarlo a la vitrina."),
            imagenResourceId = R.drawable.pasteleria_vitrina_final,
            videoResourceId = R.raw.clase_pasteleria_terminaciones
        ),
        Leccion(
            id = 301,
            cursoId = 3,
            numero = 1,
            titulo = "Ingredientes y utensilios",
            descripcion = "Identifica los ingredientes y herramientas para trabajar masas.",
            duracionMinutos = 12,
            materiales = listOf("Harina", "Levadura", "Bowl", "Balanza", "Espátula"),
            pasos = listOf("Lee la receta completa.", "Reúne y pesa los ingredientes.", "Comprueba que los utensilios estén limpios y listos."),
            imagenResourceId = R.drawable.masas_ingredientes
        ),
        Leccion(
            id = 302,
            cursoId = 3,
            numero = 2,
            titulo = "Mezclado y amasado",
            descripcion = "Integra los ingredientes y trabaja la masa de forma uniforme.",
            duracionMinutos = 20,
            materiales = listOf("Ingredientes pesados", "Bowl", "Mesón", "Espátula"),
            pasos = listOf("Incorpora los ingredientes siguiendo la receta.", "Mezcla hasta formar una masa.", "Amasa hasta lograr una textura uniforme.", "Deja reposar si la receta lo indica."),
            imagenResourceId = R.drawable.masas_amasado,
            videoResourceId = R.raw.clase_masas_amasado
        ),
        Leccion(
            id = 303,
            cursoId = 3,
            numero = 3,
            titulo = "Fermentación inicial",
            descripcion = "Deja reposar la masa para que desarrolle volumen y textura.",
            duracionMinutos = 30,
            materiales = listOf("Masa preparada", "Bowl", "Paño limpio"),
            pasos = listOf("Coloca la masa en un bowl.", "Cúbrela para evitar que se reseque.", "Déjala reposar según la receta.", "Comprueba el cambio de volumen antes de continuar."),
            imagenResourceId = R.drawable.masas_fermentacion
        ),
        Leccion(
            id = 304,
            cursoId = 3,
            numero = 4,
            titulo = "División y formado",
            descripcion = "Divide la masa y da forma a las piezas de bollería.",
            duracionMinutos = 18,
            materiales = listOf("Masa fermentada", "Balanza", "Cuchillo", "Bandeja"),
            pasos = listOf("Divide la masa en porciones similares.", "Deja reposar las porciones si la receta lo indica.", "Forma cada pieza con cuidado.", "Ordena las piezas en una bandeja."),
            imagenResourceId = R.drawable.masas_formado
        ),
        Leccion(
            id = 305,
            cursoId = 3,
            numero = 5,
            titulo = "Fermentación final y horneado",
            descripcion = "Prepara las piezas para el horneado siguiendo la receta.",
            duracionMinutos = 25,
            materiales = listOf("Piezas formadas", "Bandeja", "Horno", "Brocha"),
            pasos = listOf("Deja reposar las piezas según la receta.", "Precalienta el horno a la temperatura indicada.", "Aplica un acabado si corresponde.", "Hornea y deja enfriar antes de manipular."),
            imagenResourceId = R.drawable.masas_horneado
        ),
        Leccion(
            id = 306,
            cursoId = 3,
            numero = 6,
            titulo = "Acabados y presentación",
            descripcion = "Revisa y prepara las piezas para servir o exhibir.",
            duracionMinutos = 12,
            materiales = listOf("Piezas horneadas", "Decoración", "Bandeja", "Envase"),
            pasos = listOf("Comprueba que las piezas estén frías.", "Agrega el acabado o decoración elegida.", "Ordena las piezas para presentarlas.", "Prepara tu resultado para fotografiarlo."),
            imagenResourceId = R.drawable.masas_presentacion
        )
    )

    fun obtenerLeccionesPorCurso(cursoId: Int): List<Leccion> =
        lecciones.filter { it.cursoId == cursoId }.sortedBy { it.numero }

    fun obtenerLeccionPorId(id: Int): Leccion? =
        lecciones.find { it.id == id }
}
