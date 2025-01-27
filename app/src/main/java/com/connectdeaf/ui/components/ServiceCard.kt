package com.connectdeaf.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.connectdeaf.R
import com.connectdeaf.ui.theme.ErrorColor
import com.connectdeaf.ui.theme.PrimaryColor

@Composable
fun ServiceCard(
    id: String,
    name: String, // Nome do serviço
    description: String,
    image: String? = null,
    value: Double,
    onClick: (id: String) -> Unit,
    isProfessional: Boolean,
    onDeleteClick: (id: String) -> Unit = {},
    onEditClick: (id: String) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .clickable { onClick(id) }
            .fillMaxWidth()
            .widthIn(max = 180.dp)
            .background(Color.White)
            .height(218.dp),
        shape = RoundedCornerShape(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            ) {
                if (image == null) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black),
                        contentAlignment = Alignment.Center
                    ) {
                        // Placeholder: Pode adicionar um ícone ou texto de placeholder aqui
                    }
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.doutor), // Usando uma imagem padrão
                        contentDescription = "Service Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            // Nome do serviço
            Column(
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = name,
                    fontSize = 16.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }

            // Value and Description
            Column(
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "A partir de R$ $value", fontSize = 14.sp, color = Color.Black)
                Text(
                    text = description,
                    fontSize = 12.sp,
                    color = Color.Gray,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }

            if (isProfessional) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 15.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(onClick = { onEditClick(id) }) {
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = "Editar",
                            tint = PrimaryColor,

                        )
                    }
                    IconButton(onClick = { onDeleteClick(id) }) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Deletar",
                            tint = ErrorColor,
                            modifier = Modifier.size(50.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun ServiceCardPreview() {
    ServiceCard(
        id = "1",
        name = "Serviço de Limpeza",
        description = "Oferecemos serviços de limpeza residencial e comercial com alta qualidade e preços competitivos.",
        image = null,
        value = 150.00,
        onClick = { id -> println("Clicked on service with id: $id") },
        isProfessional = true,
        onDeleteClick = { id -> println("Delete clicked on service with id: $id") },
        onEditClick = { id -> println("Edit clicked on service with id: $id") }
    )
}
