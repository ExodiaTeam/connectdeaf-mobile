package com.connectdeaf.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.connectdeaf.R
import com.connectdeaf.ui.theme.ErrorColor
import com.connectdeaf.ui.theme.GreyLighter
import com.connectdeaf.ui.theme.PrimaryColor

@Composable
fun ServiceCard(
    id: String,
    description: String,
    image: String? = null,
    value: String,
    isProfessional: Boolean, // Define se o usuário é profissional
    onClick: (id: String) -> Unit,
    onEdit: (id: String) -> Unit = {},
    onDelete: (id: String) -> Unit = {}
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
        Box(modifier = Modifier.fillMaxSize()) {
            Column {
                // Imagem ou Placeholder
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
                        ) {}
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.doutor),
                            contentDescription = "Service Image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }

                Column(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(text = "A partir de $value", fontSize = 14.sp, color = Color.Black)
                    Text(
                        text = description,
                        fontSize = 12.sp,
                        color = Color.Gray,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            if (isProfessional) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Row {
                        IconButton(onClick = { onEdit(id) }) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Editar Serviço",
                                tint = PrimaryColor
                            )
                        }
                        IconButton(onClick = { onDelete(id) }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Excluir Serviço",
                                tint = ErrorColor
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun ServiceCardPreview() {
    Column {
        // Versão para cliente
        ServiceCard(
            id = "1",
            description = "Serviço de Limpeza Profissional.",
            image = null,
            value = "R$ 150,00",
            isProfessional = false,
            onClick = { id -> println("Clicked on service with id: $id") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Versão para profissional (com editar e deletar)
        ServiceCard(
            id = "2",
            description = "Consultoria de TI Personalizada.",
            image = null,
            value = "R$ 200,00",
            isProfessional = true,
            onClick = { id -> println("Clicked on service with id: $id") },
            onEdit = { id -> println("Editing service with id: $id") },
            onDelete = { id -> println("Deleting service with id: $id") }
        )
    }
}
