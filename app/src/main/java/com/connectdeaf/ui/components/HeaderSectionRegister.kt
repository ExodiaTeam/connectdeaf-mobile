package com.connectdeaf.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HeaderSectionRegister(isProfessional: Boolean = false, isService: Boolean = false) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isService) {
            ServiceSection()
        } else {
            CadastroTitle()
            CadastroDescription(isProfessional)
        }

    }
}

@Composable
fun CadastroTitle() {
    Text(
        text = "Cadastro",
        fontSize = 20.sp,
        fontFamily = FontFamily.SansSerif,
        color = Color.Black,
        modifier = Modifier.padding(bottom = 4.dp)
    )
}

@Composable
fun CadastroDescription(isProfessional: Boolean) {
    Text(
        text = if (isProfessional) "Me fala mais sobre você, profissional!"
        else "Me fala mais sobre você, cliente!",
        fontSize = 16.sp,
        fontFamily = FontFamily.SansSerif,
        color = Color.Black,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@Composable
fun ServiceSection() {
    Text(
        text = "Cadastro de Serviço",
        fontSize = 20.sp,
        fontFamily = FontFamily.SansSerif,
        color = Color.Black,
        modifier = Modifier.padding(bottom = 4.dp)
    )
    Text(
        text = "Informações básicas do seu serviço.",
        fontSize = 16.sp,
        fontFamily = FontFamily.SansSerif,
        color = Color.Black,
        modifier = Modifier.padding(bottom = 8.dp)
    )

}
