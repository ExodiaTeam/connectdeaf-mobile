package com.connectdeaf.ui.screens

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.connectdeaf.R
import com.connectdeaf.ui.components.ChipComponent
import com.connectdeaf.ui.components.DrawerMenu
import com.connectdeaf.ui.components.SearchBarField
import com.connectdeaf.ui.theme.PrimaryColor
import com.connectdeaf.viewmodel.DrawerViewModel
import com.connectdeaf.viewmodel.ServicesViewModel
import kotlinx.coroutines.launch


@Composable
fun HomeScreen(navController: NavController, drawerViewModel : DrawerViewModel, servicesViewModel: ServicesViewModel) {
    val scope = rememberCoroutineScope()

    DrawerMenu(
        navController = navController,
        scope = scope,
        drawerViewModel = drawerViewModel
    ) {
        Scaffold(
            topBar = {
                com.connectdeaf.ui.components.TopAppBar(
                    onOpenDrawerMenu = { scope.launch { drawerViewModel.openMenuDrawer() } },
                    onOpenDrawerNotifications = { scope.launch { drawerViewModel.openNotificationsDrawer() } },
                    showBackButton = false,
                    navController = navController
                )
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

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SearchBarField(
                        searchQuery = servicesViewModel.searchQuery.collectAsState().value,
                        onSearchQueryChange = { newValue -> servicesViewModel.onSearchQueryChange(newValue) },
                        placeholder = "Pesquisar por serviço...",
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    IconButton(
                        onClick = { navController.navigate("services") },
                        modifier = Modifier.size(56.dp).background(Color(0xFFE2E8F7), shape = RoundedCornerShape(8.dp))
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Pesquisar",
                            tint = Color.Black,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }



                Spacer(modifier = Modifier.height(16.dp))

                TagsSection(
                    tags = listOf("Designer", "IA", "Outra", "Tag", "Desenvolvimento"),
                    onTagSelected = {
                        servicesViewModel.onSearchQueryChange(TextFieldValue(it))
                        navController.navigate("services")
                    }
                )

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
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TagsSection(tags: List<String>, onTagSelected: (String) -> Unit) {
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
                ChipComponent(
                    text = tag,
                    onClick = onTagSelected
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen( navController = NavHostController(LocalContext.current), drawerViewModel = DrawerViewModel(), servicesViewModel = ServicesViewModel(LocalContext.current))
}