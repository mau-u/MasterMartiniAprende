package com.example.mastermartiniaprende.ui.components

import android.net.Uri
import android.widget.MediaController
import android.widget.VideoView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.unit.dp

@Composable
fun ReproductorVideoLocal(
    videoResourceId: Int,
    imagenPortada: Int,
    titulo: String
) {
    val context = LocalContext.current
    var mostrarReproductor by remember(videoResourceId) { mutableStateOf(false) }
    var errorReproduccion by remember(videoResourceId) { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
            ) {
                if (mostrarReproductor && !errorReproduccion) {
                    val videoView = remember(context, videoResourceId) {
                        VideoView(context).apply {
                            setMediaController(MediaController(context))
                            setVideoURI(
                                Uri.parse("android.resource://${context.packageName}/$videoResourceId")
                            )
                            setOnPreparedListener { reproductor ->
                                reproductor.isLooping = false
                                start()
                            }
                            setOnErrorListener { _, _, _ ->
                                errorReproduccion = true
                                true
                            }
                        }
                    }

                    DisposableEffect(videoView) {
                        onDispose { videoView.stopPlayback() }
                    }

                    AndroidView(
                        factory = { videoView },
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp))
                    )
                } else {
                    Image(
                        painter = painterResource(imagenPortada),
                        contentDescription = "Portada del video: $titulo",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp)),
                        contentScale = ContentScale.Crop
                    )

                    if (!errorReproduccion) {
                        Button(
                            onClick = { mostrarReproductor = true },
                            modifier = Modifier.align(Alignment.Center)
                        ) {
                            Text("▶ Reproducir video")
                        }
                    }
                }
            }

            Column(modifier = Modifier.padding(14.dp)) {
                Text(titulo, style = MaterialTheme.typography.titleMedium)
                if (errorReproduccion) {
                    Text(
                        text = "No se pudo reproducir el video. Intenta abrirlo nuevamente.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                    Button(
                        onClick = {
                            errorReproduccion = false
                            mostrarReproductor = false
                        }
                    ) {
                        Text("Intentar de nuevo")
                    }
                } else {
                    Text(
                        text = "Video guardado en la app; no necesita YouTube ni conexión.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}
