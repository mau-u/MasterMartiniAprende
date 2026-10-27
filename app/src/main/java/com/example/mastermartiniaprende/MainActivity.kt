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
import androidx.compose.runtime.LaunchedEffect
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
import com.example.mastermartiniaprende.data.local.SessionManager
import com.example.mastermartiniaprende.repository.AppRepository
import com.example.mastermartiniaprende.ui.components.BarraNavegacionPrincipal
import com.example.mastermartiniaprende.ui.screens.CatalogoScreen
import com.example.mastermartiniaprende.ui.screens.DetalleCursoScreen
import com.example.mastermartiniaprende.ui.screens.DetalleLeccionScreen
import com.example.mastermartiniaprende.ui.screens.HomeScreen
import com.example.mastermartiniaprende.ui.screens.LoginScreen
import com.example.mastermartiniaprende.ui.screens.MisCursosScreen
import com.example.mastermartiniaprende.ui.screens.MisLogrosScreen
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

                    val context =
                        LocalContext.current.applicationContext

                    // Administrador de la sesión
                    val sessionManager = remember(context) {
                        SessionManager(context)
                    }

                    // Elegir la pantalla inicial
                    val destinoInicial = remember(sessionManager) {
                        if (sessionManager.haySesionActiva()) {
                            "restaurarSesion"
                        } else {
                            "register"
                        }
                    }

                    // Base de datos y repositorio
                    val appRepository = remember(context) {
                        val dao =
                            AppDatabase.getDatabase(context).appDao()

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

                    val backStackEntry by
                    navController.currentBackStackEntryAsState()

                    val rutaActual =
                        backStackEntry?.destination?.route

                    val mostrarNavegacion =
                        rutaActual != null &&
                                rutaActual !in listOf(
                            "register",
                            "login",
                            "restaurarSesion",
                            "misLogros"
                        )

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
                            startDestination = destinoInicial,
                            modifier = Modifier.padding(paddingContenido)
                        ) {

                            // 1. Recuperar la sesión anterior
                            composable("restaurarSesion") {

                                val emailGuardado =
                                    sessionManager.obtenerEmailSesion()

                                LaunchedEffect(emailGuardado) {

                                    if (emailGuardado.isNullOrBlank()) {

                                        sessionManager.cerrarSesion()

                                        navController.navigate("register") {
                                            popUpTo("restaurarSesion") {
                                                inclusive = true
                                            }
                                            launchSingleTop = true
                                        }

                                    } else {

                                        authViewModel.restaurarSesion(
                                            emailGuardado
                                        ) { usuario ->

                                            if (usuario == null) {

                                                sessionManager.cerrarSesion()

                                                navController.navigate("register") {
                                                    popUpTo("restaurarSesion") {
                                                        inclusive = true
                                                    }
                                                    launchSingleTop = true
                                                }

                                            } else {

                                                catalogoViewModel.cargarUsuario(
                                                    usuario.email
                                                ) {
                                                    navController.navigate("home") {
                                                        popUpTo("restaurarSesion") {
                                                            inclusive = true
                                                        }
                                                        launchSingleTop = true
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }

                                Text(
                                    text = "Recuperando tu sesión...",
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }

                            // 2. Registro
                            composable("register") {
                                RegistroScreen(
                                    navController = navController,
                                    viewModel = authViewModel
                                )
                            }

                            // 3. Inicio de sesión
                            composable("login") {
                                LoginScreen(
                                    navController = navController,
                                    viewModel = authViewModel,
                                    onLoginExitoso = { email ->

                                        sessionManager.guardarSesion(email)

                                        catalogoViewModel.cargarUsuario(email) {
                                            navController.navigate("home") {
                                                popUpTo("login") {
                                                    inclusive = true
                                                }
                                                launchSingleTop = true
                                            }
                                        }
                                    }
                                )
                            }

                            // 4. Inicio
                            composable("home") {
                                HomeScreen(
                                    navController = navController,
                                    viewModel = authViewModel,
                                    onCerrarSesion = {

                                        sessionManager.cerrarSesion()
                                        authViewModel.cerrarSesion()
                                        catalogoViewModel.cerrarSesion()

                                        navController.navigate("register") {
                                            popUpTo("home") {
                                                inclusive = true
                                            }
                                            launchSingleTop = true
                                        }
                                    }
                                )
                            }

                            // 5. Catálogo
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

                            // 6. Mis cursos
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

                            // 7. Perfil y acceso a Mis logros
                            composable("perfil") {
                                PerfilScreen(
                                    viewModel = authViewModel,
                                    onCerrarSesion = {

                                        sessionManager.cerrarSesion()
                                        authViewModel.cerrarSesion()
                                        catalogoViewModel.cerrarSesion()

                                        navController.navigate("register") {
                                            popUpTo("home") {
                                                inclusive = true
                                            }
                                            launchSingleTop = true
                                        }
                                    },
                                    onVerLogros = {
                                        navController.navigate("misLogros")
                                    }
                                )
                            }

                            // 8. Mis logros y fotografías
                            composable("misLogros") {
                                MisLogrosScreen(
                                    viewModel = catalogoViewModel,
                                    onVolver = {
                                        navController.popBackStack()
                                    }
                                )
                            }

                            // 9. Detalle del curso
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
                                        leccionViewModel = leccionViewModel,
                                        catalogoViewModel = catalogoViewModel,
                                        onLeccionClick = { leccionId ->
                                            catalogoViewModel.inscribirCurso(
                                                cursoId
                                            )

                                            navController.navigate(
                                                "detalleLeccion/$leccionId"
                                            )
                                        },
                                        onVolver = {
                                            navController.popBackStack()
                                        },
                                        onResultadoClick = {
                                            navController.navigate(
                                                "resultadoFinal/$cursoId"
                                            )
                                        }
                                    )
                                } else {
                                    Text("No se encontró el curso.")
                                }
                            }

                            // 10. Detalle de la lección
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
                                    leccionViewModel.obtenerLeccionPorId(
                                        leccionId
                                    )

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

                            // 11. Resultado final del curso
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
    }
}