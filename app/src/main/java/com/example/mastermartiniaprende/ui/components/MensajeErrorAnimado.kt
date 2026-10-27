package com.example.mastermartiniaprende.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun MensajeErrorAnimado(
    mensaje: String?,
    mensajeAyuda: String? = null
) {
    Column {
        AnimatedVisibility(
            visible = !mensaje.isNullOrBlank(),
            enter = fadeIn(
                animationSpec = tween(220)
            ) + slideInVertically(
                initialOffsetY = { altura -> -altura / 3 },
                animationSpec = tween(220)
            ),
            exit = fadeOut(
                animationSpec = tween(150)
            ) + shrinkVertically(
                animationSpec = tween(150)
            )
        ) {
            Text(
                text = mensaje.orEmpty(),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        if (mensaje.isNullOrBlank() && !mensajeAyuda.isNullOrBlank()) {
            Text(
                text = mensajeAyuda,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}