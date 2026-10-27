package com.example.mastermartiniaprende.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mastermartiniaprende.R
import com.example.mastermartiniaprende.ui.components.MarcaMasterMartini
import com.example.mastermartiniaprende.ui.theme.CacaoOscuro
import com.example.mastermartiniaprende.viewmodel.AuthViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: AuthViewModel,
    onCerrarSesion: () -> Unit
) {
    var mostrarContenido by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { mostrarContenido = true }

    val email = viewModel.usuarioActual.value ?: "Usuario"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 18.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        MarcaMasterMartini(grande = true)

        AnimatedVisibility(
            visible = mostrarContenido,
            enter = fadeIn(animationSpec = tween(450)) +
                slideInVertically(
                    initialOffsetY = { it / 14 },
                    animationSpec = tween(450)
                )
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(18.dp)) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "¡Hola, qué bueno verte!",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Continúa aprendiendo a tu ritmo.",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                Card(shape = RoundedCornerShape(24.dp)) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp)
                            .clip(RoundedCornerShape(24.dp))
                    ) {
                        Image(
                            painter = painterResource(R.drawable.foto_chocolate_pexels),
                            contentDescription = "Pastel decorado con chocolate en una cocina de pastelería",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Transparent,
                                            CacaoOscuro.copy(alpha = 0.88f)
                                        )
                                    )
                                )
                        )
                        Column(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(20.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = "Aprende haciendo",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = "Técnicas para hacer crecer tu negocio gastronómico.",
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.White
                            )
                        }
                    }
                }

                Text(
                    text = "Tu espacio de aprendizaje",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold
                )

                Button(
                    onClick = { navController.navigate("catalogo") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 60.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Explorar cursos", style = MaterialTheme.typography.titleMedium)
                        Text("→", style = MaterialTheme.typography.headlineSmall)
                    }
                }

                OutlinedButton(
                    onClick = { navController.navigate("misCursos") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 60.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(horizontalAlignment = Alignment.Start) {
                            Text("Mis cursos", style = MaterialTheme.typography.titleMedium)
                            Text("Revisa tu avance", style = MaterialTheme.typography.bodyMedium)
                        }
                        Text("→", style = MaterialTheme.typography.headlineSmall)
                    }
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .semantics { contentDescription = "Sesión iniciada con $email" },
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text("Tu cuenta", style = MaterialTheme.typography.titleMedium)
                        Text(email, style = MaterialTheme.typography.bodyLarge)
                    }
                }

                OutlinedButton(
                    onClick = onCerrarSesion,
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 56.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("Cerrar sesión", style = MaterialTheme.typography.titleMedium)
                }

                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}
