package com.connectdeaf.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.connectdeaf.R
import com.connectdeaf.ui.theme.PrimaryColor
import com.connectdeaf.ui.theme.TextColorPrimary


@Composable
fun HomeScreen(onSearch: (String) -> Unit) {
    Scaffold(
        topBar = {
            com.connectdeaf.ui.components.TopAppBar(navController = null, showBackButton = false)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = buildAnnotatedString {
                    append("Encontre o profissional ")
                    withStyle(
                        style = SpanStyle(
                            color = PrimaryColor,
                        )
                    ) {
                        append("capacitado ")
                    }
                    append("para o seu serviço ")
                    withStyle(
                        style = SpanStyle(
                            color = PrimaryColor,
                        )
                    ) {
                        append("imediatamente.")
                    }
                },
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.padding(top = 16.dp),
            )

            Spacer(modifier = Modifier.height(24.dp))

            SearchBar(onSearch)

            Spacer(modifier = Modifier.height(16.dp))

            TagsSection(tags = listOf("Designer", "IA", "Outra", "Tag", "Desenvolvimento"))

            Spacer(modifier = Modifier.weight(1f))

            Image(
                painter = painterResource(id = R.drawable.home_image),
                contentDescription = "Ilustração Home",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 40.dp),
                contentScale = ContentScale.Crop,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(onSearch: (String) -> Unit) {
    var searchText = remember { TextFieldValue() }
    OutlinedTextField(
        value = searchText,
        onValueChange = { searchText = it },
        placeholder = { Text(text = "Pesquisar por serviço...", color = Color(0xFF999999)) },
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color(0xFF478FCC),
            unfocusedBorderColor = Color(0xFF999999)
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Search",
                tint = Color(0xFF999999)
            )
        }
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TagsSection(tags: List<String>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        Text(
            text = "Mais procurados:",
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                textAlign = TextAlign.Start
            )
        )

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            tags.forEach { tag ->
                HomeChip(tag)
            }
        }
    }
}

@Composable
fun HomeChip(text: String) {
    ElevatedAssistChip(
        onClick = { /* TODO: Tag Action */ },
        shape = RoundedCornerShape(16.dp),
        label = { Text(text = text, style = TextStyle(fontSize = 14.sp)) },
        colors = AssistChipDefaults.assistChipColors(
            containerColor = Color(0xFFE2E8F7),
            labelColor = TextColorPrimary
        )
    )
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(onSearch = {})
}
