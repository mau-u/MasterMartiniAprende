package com.example.mastermartiniaprende

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mastermartiniaprende.data.local.AppDatabase
import com.example.mastermartiniaprende.repository.AppRepository
import com.example.mastermartiniaprende.ui.components.BarraNavegacionPrincipal
import com.example.mastermartiniaprende.ui.screens.CatalogoScreen

import com.example.mastermartiniaprende.ui.screens.DetalleCursoScreen
import com.example.mastermartiniaprende.ui.screens.DetalleLeccionScreen
import com.example.mastermartiniaprende.ui.screens.HomeScreen
import com.example.mastermartiniaprende.ui.screens.LoginScreen
import com.example.mastermartiniaprende.ui.screens.MisCursosScreen
import com.example.mastermartiniaprende.ui.screens.PerfilScreen
import com.example.mastermartiniaprende.ui.screens.RegistroScreen
import com.example.mastermartiniaprende.ui.screens.ResultadoFinalScreen
import com.example.mastermartiniaprende.ui.theme.MasterMartiniAprendeTheme
import com.example.mastermartiniaprende.viewmodel.AuthViewModel
import com.example.mastermartiniaprende.viewmodel.CatalogoViewModel
import com.example.mastermartiniaprende.viewmodel.LeccionViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MasterMartiniAprendeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val context = LocalContext.current.applicationContext
                    val appRepository = remember(context) {
                        val dao = AppDatabase.getDatabase(context).appDao()
                        AppRepository(dao)
                    }
                    val navController = rememberNavController()
                    val authViewModel = remember(appRepository) {
                        AuthViewModel(appRepository)
                    }
                    val catalogoViewModel = remember(appRepository) {
                        CatalogoViewModel(appRepository)
                    }
                    val leccionViewModel = remember {
                        LeccionViewModel()
                    }
                    val backStackEntry by navController.currentBackStackEntryAsState()
                    val rutaActual = backStackEntry?.destination?.route
                    val mostrarNavegacion = rutaActual != null &&
                        rutaActual !in listOf("register", "login")

                    Scaffold(
                        bottomBar = {
                            if (mostrarNavegacion) {
                                BarraNavegacionPrincipal(
                                    navController = navController,
                                    rutaActual = rutaActual
                                )
                            }
                        }
                    ) { paddingContenido ->
                        NavHost(
                            navController = navController,
                            startDestination = "register",
                            modifier = Modifier.padding(paddingContenido)
                        ) {
                            composable("register") {
                                RegistroScreen(
                                    navController = navController,
                                    viewModel = authViewModel
                                )
                            }

                            composable("login") {
                                LoginScreen(
                                    navController = navController,
                                    viewModel = authViewModel,
                                    onLoginExitoso = { email ->
                                        catalogoViewModel.cargarUsuario(email) {
                                            navController.navigate("home") {
                                                popUpTo("login") { inclusive = true }
                                                launchSingleTop = true
                                            }
                                        }
                                    }
                                )
                            }

                            composable("home") {
                                HomeScreen(
                                    navController = navController,
                                    viewModel = authViewModel,
                                    onCerrarSesion = {
                                        authViewModel.cerrarSesion()
                                        catalogoViewModel.cerrarSesion()
                                        navController.navigate("register") {
                                            popUpTo("home") { inclusive = true }
                                            launchSingleTop = true
                                        }
                                    }
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

                            composable("misCursos") {
                                MisCursosScreen(
                                    viewModel = catalogoViewModel,
                                    onCursoClick = { cursoId ->
                                        navController.navigate("detalleCurso/$cursoId")
                                    },
                                    onExplorarCursos = {
                                        navController.navigate("catalogo") {
                                            launchSingleTop = true
                                        }
                                    }
                                )
                            }

                            composable("perfil") {
                                PerfilScreen(
                                    viewModel = authViewModel,
                                    onCerrarSesion = {
                                        authViewModel.cerrarSesion()
                                        catalogoViewModel.cerrarSesion()
                                        navController.navigate("register") {
                                            popUpTo("home") { inclusive = true }
                                            launchSingleTop = true
                                        }
                                    }
                                )
                            }

                            composable(
                                route = "detalleCurso/{cursoId}",
                                arguments = listOf(navArgument("cursoId") { type = NavType.IntType })
                            ) { entry ->
                                val cursoId = entry.arguments?.getInt("cursoId") ?: -1
                                val curso = catalogoViewModel.obtenerCursoPorId(cursoId)

                                if (curso != null) {
                                    DetalleCursoScreen(
                                        curso = curso,
                                        leccionViewModel = leccionViewModel,
                                        catalogoViewModel = catalogoViewModel,
                                        onLeccionClick = { leccionId ->
                                            catalogoViewModel.inscribirCurso(cursoId)
                                            navController.navigate("detalleLeccion/$leccionId")
                                        },
                                        onVolver = { navController.popBackStack() },
                                        onResultadoClick = {
                                            navController.navigate("resultadoFinal/$cursoId")
                                        }
                                    )
                                } else {
                                    Text("No se encontró el curso.")
                                }
                            }


                            composable(
                                route = "detalleLeccion/{leccionId}",
                                arguments = listOf(navArgument("leccionId") { type = NavType.IntType })
                            ) { entry ->
                                val leccionId = entry.arguments?.getInt("leccionId") ?: -1
                                val leccion = leccionViewModel.obtenerLeccionPorId(leccionId)

                                if (leccion != null) {
                                    DetalleLeccionScreen(
                                        leccion = leccion,
                                        onVolver = { navController.popBackStack() },
                                        onCompletar = {
                                            if (!catalogoViewModel.estaClaseCompletada(
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

                            composable(
                                route = "resultadoFinal/{cursoId}",
                                arguments = listOf(navArgument("cursoId") { type = NavType.IntType })
                            ) { entry ->
                                val cursoId = entry.arguments?.getInt("cursoId") ?: -1
                                val curso = catalogoViewModel.obtenerCursoPorId(cursoId)

                                if (curso != null) {
                                    ResultadoFinalScreen(
                                        curso = curso,
                                        viewModel = catalogoViewModel,
                                        onVolver = { navController.popBackStack() }
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
    }
}
