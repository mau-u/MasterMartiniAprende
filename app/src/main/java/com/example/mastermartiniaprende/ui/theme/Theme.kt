package com.example.mastermartiniaprende.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = TerracotaClara,
    onPrimary = CacaoOscuro,
    primaryContainer = Cacao,
    onPrimaryContainer = CremaPastel,
    secondary = DoradoSuave,
    onSecondary = CacaoOscuro,
    tertiary = VerdePistacho,
    onTertiary = CacaoOscuro,
    background = CacaoNoche,
    onBackground = CremaPastel,
    surface = CacaoNoche,
    onSurface = CremaPastel,
    surfaceVariant = CacaoMedio,
    onSurfaceVariant = RosaMasa
)

private val LightColorScheme = lightColorScheme(
    primary = Terracota,
    onPrimary = Blanco,
    primaryContainer = RosaMasa,
    onPrimaryContainer = CacaoOscuro,
    secondary = DoradoSuave,
    onSecondary = CacaoOscuro,
    tertiary = VerdePistacho,
    onTertiary = Blanco,
    background = CremaPastel,
    onBackground = CacaoOscuro,
    surface = CremaSuperficie,
    onSurface = CacaoOscuro,
    surfaceVariant = Melocoton,
    onSurfaceVariant = Cacao
)

@Composable
fun MasterMartiniAprendeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
