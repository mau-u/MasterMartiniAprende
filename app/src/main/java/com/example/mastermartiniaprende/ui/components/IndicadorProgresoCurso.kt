package com.example.mastermartiniaprende.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mastermartiniaprende.model.Curso

@Composable
fun IndicadorProgresoCurso(
    curso: Curso,
    clasesCompletadas: Int,
    modifier: Modifier = Modifier
) {
    val porcentaje = if (curso.cantidadClases == 0) 0
    else (clasesCompletadas * 100 / curso.cantidadClases).coerceIn(0, 100)
    val progresoAnimado by animateFloatAsState(
        targetValue = porcentaje / 100f,
        label = "progresoCurso${curso.id}"
    )
    val textoLogro = when {
        porcentaje == 100 -> "🏆 Logro desbloqueado: curso completado"
        porcentaje >= 75 -> "🌟 ¡Ya casi terminas el curso!"
        porcentaje >= 50 -> "👏 ¡Vas por más de la mitad!"
        porcentaje > 0 -> "🌱 Buen comienzo, sigue avanzando"
        else -> "🎯 Completa tu primera clase para iniciar"
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (porcentaje == 100) {
                MaterialTheme.colorScheme.tertiaryContainer
            } else {
                MaterialTheme.colorScheme.secondaryContainer
            }
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Tu avance",
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "$porcentaje%",
                    style = MaterialTheme.typography.titleMedium
                )
            }
            LinearProgressIndicator(
                progress = { progresoAnimado },
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "$clasesCompletadas de ${curso.cantidadClases} clases completadas",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = textoLogro,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
