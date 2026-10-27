package com.example.mastermartiniaprende.ui.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.mastermartiniaprende.R

@Composable
fun IconoErrorCampo(visible: Boolean) {
    if (visible) {
        Image(
            painter = painterResource(R.drawable.ic_error_outline),
            contentDescription = "Campo con error",
            modifier = Modifier
        )
    }
}
