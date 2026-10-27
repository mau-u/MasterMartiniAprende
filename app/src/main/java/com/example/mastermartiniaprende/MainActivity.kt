package com.example.mastermartiniaprende

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mastermartiniaprende.ui.screens.CatalogoScreen
import com.example.mastermartiniaprende.ui.screens.ClasesCursoScreen
import com.example.mastermartiniaprende.ui.screens.DetalleCursoScreen
import com.example.mastermartiniaprende.ui.screens.DetalleLeccionScreen
import com.example.mastermartiniaprende.ui.screens.HomeScreen
import com.example.mastermartiniaprende.ui.screens.LoginScreen
import com.example.mastermartiniaprende.ui.screens.MisCursosScreen
import com.example.mastermartiniaprende.ui.screens.RegistroScreen
import com.example.mastermartiniaprende.ui.screens.ResultadoFinalScreen
import com.example.mastermartiniaprende.ui.theme.MasterMartiniAulaTheme
import com.example.mastermartiniaprende.viewmodel.AuthViewModel
import com.example.mastermartiniaprende.viewmodel.CatalogoViewModel
import com.example.mastermartiniaprende.viewmodel.LeccionViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MasterMartiniAulaTheme {

                val navController = rememberNavController()

                val authViewModel = remember {
                    AuthViewModel()
                }

                val catalogoViewModel = remember {
                    CatalogoViewModel()
                }

                val leccionViewModel = remember {
                    LeccionViewModel()
                }

                NavHost(
                    navController = navController,
                    startDestination = "register"
                ) {

                    // 1. Registro
                    composable("register") {
                        RegistroScreen(
                            navController = navController,
                            viewModel = authViewModel
                        )
                    }

                    // 2. Inicio de sesión
                    composable("login") {
                        LoginScreen(
                            navController = navController,
                            viewModel = authViewModel
                        )
                    }

                    // 3. Inicio
                    composable("home") {
                        HomeScreen(
                            navController = navController,
                            viewModel = authViewModel
                        )
                    }

                    // 4. Catálogo de cursos
                    composable("catalogo") {
                        CatalogoScreen(
                            viewModel = catalogoViewModel,
                            onCursoClick = { cursoId ->
                                navController.navigate(
                                    "detalleCurso/$cursoId"
                                )
                            }
                        )
                    }

                    // 5. Mis cursos
                    composable("misCursos") {
                        MisCursosScreen(
                            viewModel = catalogoViewModel,
                            onCursoClick = { cursoId ->
                                navController.navigate(
                                    "detalleCurso/$cursoId"
                                )
                            },
                            onExplorarCursos = {
                                navController.navigate("catalogo") {
                                    launchSingleTop = true
                                }
                            }
                        )
                    }

                    // 6. Detalle del curso
                    composable(
                        route = "detalleCurso/{cursoId}",
                        arguments = listOf(
                            navArgument("cursoId") {
                                type = NavType.IntType
                            }
                        )
                    ) { entry ->

                        val cursoId =
                            entry.arguments?.getInt("cursoId") ?: -1

                        val curso =
                            catalogoViewModel.obtenerCursoPorId(cursoId)

                        if (curso != null) {
                            DetalleCursoScreen(
                                curso = curso,
                                onVolver = {
                                    navController.popBackStack()
                                },
                                onComenzar = {
                                    catalogoViewModel.inscribirCurso(cursoId)

                                    navController.navigate(
                                        "clasesCurso/$cursoId"
                                    )
                                }
                            )
                        } else {
                            Text("No se encontró el curso.")
                        }
                    }

                    // 7. Lista de clases del curso
                    composable(
                        route = "clasesCurso/{cursoId}",
                        arguments = listOf(
                            navArgument("cursoId") {
                                type = NavType.IntType
                            }
                        )
                    ) { entry ->

                        val cursoId =
                            entry.arguments?.getInt("cursoId") ?: -1

                        val curso =
                            catalogoViewModel.obtenerCursoPorId(cursoId)

                        if (curso != null) {
                            ClasesCursoScreen(
                                curso = curso,
                                viewModel = catalogoViewModel,
                                leccionViewModel = leccionViewModel,

                                onLeccionClick = { leccionId ->
                                    navController.navigate(
                                        "detalleLeccion/$leccionId"
                                    )
                                },

                                // NUEVO: abrir el resultado final
                                onResultadoClick = {
                                    navController.navigate(
                                        "resultadoFinal/${curso.id}"
                                    )
                                },

                                onVolver = {
                                    navController.popBackStack()
                                }
                            )
                        } else {
                            Text("No se encontró el curso.")
                        }
                    }

                    // 8. Contenido individual de una lección
                    composable(
                        route = "detalleLeccion/{leccionId}",
                        arguments = listOf(
                            navArgument("leccionId") {
                                type = NavType.IntType
                            }
                        )
                    ) { entry ->

                        val leccionId =
                            entry.arguments?.getInt("leccionId") ?: -1

                        val leccion =
                            leccionViewModel.obtenerLeccionPorId(leccionId)

                        if (leccion != null) {
                            DetalleLeccionScreen(
                                leccion = leccion,
                                onVolver = {
                                    navController.popBackStack()
                                },
                                onCompletar = {

                                    if (
                                        !catalogoViewModel.estaClaseCompletada(
                                            cursoId = leccion.cursoId,
                                            numeroClase = leccion.numero
                                        )
                                    ) {
                                        catalogoViewModel.alternarClaseCompletada(
                                            cursoId = leccion.cursoId,
                                            numeroClase = leccion.numero
                                        )
                                    }

                                    navController.popBackStack()
                                }
                            )
                        } else {
                            Text("No se encontró la lección.")
                        }
                    }

                    // 9. Resultado final del curso
                    composable(
                        route = "resultadoFinal/{cursoId}",
                        arguments = listOf(
                            navArgument("cursoId") {
                                type = NavType.IntType
                            }
                        )
                    ) { entry ->

                        val cursoId =
                            entry.arguments?.getInt("cursoId") ?: -1

                        val curso =
                            catalogoViewModel.obtenerCursoPorId(cursoId)

                        if (curso != null) {
                            ResultadoFinalScreen(
                                curso = curso,
                                viewModel = catalogoViewModel,
                                onVolver = {
                                    navController.popBackStack()
                                }
                            )
                        } else {
                            Text("No se encontró el curso.")
                        }
                    }
                }
            }
        }
    }
}