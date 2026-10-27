package com.example.mastermartiniaprende.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.mastermartiniaprende.R

private data class DestinoPrincipal(
    val ruta: String,
    val etiqueta: String,
    val icono: Int
)

@Composable
fun BarraNavegacionPrincipal(
    navController: NavController,
    rutaActual: String?
) {
    val destinos = listOf(
        DestinoPrincipal("home", "Inicio", R.drawable.ic_nav_inicio),
        DestinoPrincipal("catalogo", "Explorar", R.drawable.ic_nav_explorar),
        DestinoPrincipal("misCursos", "Mis cursos", R.drawable.ic_nav_cursos),
        DestinoPrincipal("perfil", "Perfil", R.drawable.ic_nav_perfil)
    )
    val rutasPrincipales = destinos.map { it.ruta }.toSet()
    var destinoSeleccionado by rememberSaveable {
        mutableStateOf(rutaActual?.takeIf { it in rutasPrincipales } ?: "home")
    }

    LaunchedEffect(rutaActual) {
        rutaActual?.takeIf { it in rutasPrincipales }?.let {
            destinoSeleccionado = it
        }
    }

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        destinos.forEach { destino ->
            NavigationBarItem(
                selected = destino.ruta == destinoSeleccionado,
                onClick = {
                    destinoSeleccionado = destino.ruta

                    if (destino.ruta == "home") {
                        navController.popBackStack("home", inclusive = false)
                    } else if (rutaActual != destino.ruta) {
                        navController.navigate(destino.ruta) {
                            popUpTo("home") { saveState = false }
                            launchSingleTop = true
                            restoreState = false
                        }
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(destino.icono),
                        contentDescription = null
                    )
                },
                label = { Text(destino.etiqueta) }
            )
        }
    }
}
