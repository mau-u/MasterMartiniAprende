package com.example.mastermartiniaprende

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mastermartiniaprende.ui.screens.CatalogoScreen
import com.example.mastermartiniaprende.ui.screens.HomeScreen
import com.example.mastermartiniaprende.ui.screens.LoginScreen
import com.example.mastermartiniaprende.ui.screens.RegistroScreen
import com.example.mastermartiniaprende.ui.theme.MasterMartiniAulaTheme
import com.example.mastermartiniaprende.viewmodel.AuthViewModel
import com.example.mastermartiniaprende.viewmodel.CatalogoViewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.mastermartiniaprende.ui.screens.DetalleCursoScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MasterMartiniAulaTheme {

                val navController = rememberNavController()

                val viewModel = remember {
                    AuthViewModel()
                }

                val catalogoViewModel = remember {
                    CatalogoViewModel()
                }

                NavHost(
                    navController = navController,
                    startDestination = "register"
                ) {

                    composable("register") {
                        RegistroScreen(
                            navController = navController,
                            viewModel = viewModel
                        )
                    }

                    composable("login") {
                        LoginScreen(
                            navController = navController,
                            viewModel = viewModel
                        )
                    }

                    composable("home") {
                        HomeScreen(
                            navController = navController,
                            viewModel = viewModel
                        )
                    }

                    composable("catalogo") {
                        CatalogoScreen(
                            viewModel = catalogoViewModel,
                            onCursoClick = { cursoId ->
                                navController.navigate("detalleCurso/$cursoId")
                            }
                        )
                    }

                    composable(
                        route = "detalleCurso/{cursoId}",
                        arguments = listOf(
                            navArgument("cursoId") {
                                type = NavType.IntType
                            }
                        )
                    ) { backStackEntry ->
                        val cursoId = backStackEntry.arguments?.getInt("cursoId") ?: -1
                        val curso = catalogoViewModel.obtenerCursoPorId(cursoId)

                        if (curso != null) {
                            DetalleCursoScreen(
                                curso = curso,
                                onVolver = {
                                    navController.popBackStack()
                                },
                                onComenzar = {
                                    // Conectaremos este botón a las clases más adelante.
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}