package com.example.mastermartiniaprende.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mastermartiniaprende.R
import com.example.mastermartiniaprende.ui.theme.Cacao

@Composable
fun MarcaMasterMartini(
    modifier: Modifier = Modifier,
    grande: Boolean = false
) {
    val tamano = if (grande) 58.dp else 46.dp

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = "Logo de Master Martini Aprende",
            modifier = Modifier
                .size(tamano)
                .clip(CircleShape)
                .background(Cacao)
                .padding(if (grande) 9.dp else 7.dp)
        )
        Column(
            modifier = Modifier.padding(start = 12.dp)
        ) {
            Text(
                text = "Master Martini",
                style = if (grande) MaterialTheme.typography.titleLarge
                else MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "APRENDE",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                letterSpacing = 2.sp
            )
        }
    }
}
