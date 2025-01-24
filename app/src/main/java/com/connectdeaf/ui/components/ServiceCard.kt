package com.connectdeaf.ui.components
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
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

@Composable
fun ServiceCard(
    id: Int,
    description: String,
    image: String? = null,
    value: Double,
    onClick: (id: String) -> Unit
) {

    Card(
        modifier = Modifier
            .clickable { onClick(id.toString()) }
            .fillMaxWidth()
            .widthIn(max = 180.dp)
            .background(Color.White)
            .height(218.dp),
        shape = RoundedCornerShape(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column {
            // Image or Placeholder
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

                    }
                } else {
                    Image(
                        painter = painterResource( id = R.drawable.doutor),
                        contentDescription = "Service Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            // Value and Description
            Column(
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 4.dp)
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
    }
}


@Preview
@Composable
private fun ServiceCardPreview() {
    ServiceCard(
        id = 1,
        description = "Oferecemos serviços de limpeza residencial e comercial com alta qualidade e preços competitivos.",
        image = null,
        value = 150.00,
        onClick = { id -> println("Clicked on service with id: $id") }
    )
}