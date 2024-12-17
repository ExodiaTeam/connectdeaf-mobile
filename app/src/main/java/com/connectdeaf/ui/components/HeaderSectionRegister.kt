package com.connectdeaf.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HeaderSectionRegister(isProfessional : Boolean = false) {
    Text(
        text = "Cadastro",
        fontSize = 20.sp,
        fontFamily = FontFamily.SansSerif,
        color = Color.Black,
        modifier = Modifier.padding(6.dp)
    )
    Text(
        text = if (isProfessional) "Me fala mais sobre você, profissional!"
        else "Me fala mais sobre você, cliente!",
        fontSize = 16.sp,
        fontFamily = FontFamily.SansSerif,
        color = Color.Black,
        modifier = Modifier.padding(8.dp)
    )
}