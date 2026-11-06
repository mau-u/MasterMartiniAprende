package com.example.mastermartiniaprende.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.mastermartiniaprende.R

@Composable
fun MarcaMasterMartini(
    modifier: Modifier = Modifier,
    grande: Boolean = false
) {
    Image(
        painter = painterResource(R.drawable.logo_master_martini),
        contentDescription = "Logo de Master Martini",
        modifier = modifier
            .width(if (grande) 172.dp else 132.dp)
            .aspectRatio(160f / 110f),
        contentScale = ContentScale.Fit
    )
}