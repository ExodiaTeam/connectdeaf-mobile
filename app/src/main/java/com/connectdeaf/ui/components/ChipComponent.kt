package com.connectdeaf.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ChipComponent(
    text: String,
    onClick: (String) -> Unit,
    containerColor: Color = Color(0xFFE2E8F7),
    labelColor: Color = Color.Black
) {

    ElevatedAssistChip(
        onClick = { onClick(text) },
        shape = RoundedCornerShape(16.dp),
        label = {
            Text(
                text = text,
                style = TextStyle(fontSize = 14.sp, color = labelColor)
            )
        },
        colors = AssistChipDefaults.assistChipColors(
            containerColor = containerColor,
            labelColor = labelColor
        ),
        modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp) // Ajuste de margem
    )
}
