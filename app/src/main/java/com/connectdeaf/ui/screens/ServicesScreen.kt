package com.connectdeaf.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.connectdeaf.ui.components.DrawerMenu
import com.connectdeaf.ui.components.DropdownMenuField
import com.connectdeaf.ui.components.SearchBarField
import com.connectdeaf.ui.components.ServiceCard
import com.connectdeaf.viewmodel.ServicesViewModel
import kotlinx.coroutines.launch

@Composable
fun ServicesScreen(
    viewModel: ServicesViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    navController: NavHostController
) {
    val serviceList = viewModel.getPaginatedList()
    val currentPage = viewModel.currentPage.value
    val searchQuery = viewModel.searchQuery.value
    val totalPages = (viewModel.serviceList.size - 1) / 10 + 1

    val CustomBlue = Color(0xFF3D66CC)
    val drawerStateMenu = rememberDrawerState(DrawerValue.Closed)
    val drawerStateNotifications = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    DrawerMenu(
        navController = navController,
        scope = scope,
        drawerStateMenu = drawerStateMenu,
        drawerStateNotifications = drawerStateNotifications
    ) {
        Scaffold(
            topBar = {
                com.connectdeaf.ui.components.TopAppBar(
                    onOpenDrawerMenu = { scope.launch { drawerStateMenu.open() } },
                    onOpenDrawerNotifications = { scope.launch { drawerStateNotifications.open() } },
                    showBackButton = false
                )
            }
        ) { paddingValues ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Header
                Text(
                    text = "Serviços",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "Talentos cuidadosamente escolhidos para suprir suas demandas profissionais.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )

                // Search Bar
                SearchBarField(
                    searchQuery = TextFieldValue(searchQuery),
                    onSearchQueryChange = { viewModel.onSearchQueryChange(it.text) },
                    placeholder = "Pesquisar por serviço...",
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Dropdown Menus
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    DropdownMenuField(
                        label = "Estado",
                        options = listOf("São Paulo", "Rio de Janeiro", "Bahia"),
                        modifier = Modifier.weight(1f)
                    )
                    DropdownMenuField(
                        label = "Cidade",
                        options = listOf("Campinas", "Niterói", "Salvador"),
                        modifier = Modifier.weight(1f)
                    )
                }

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    items(serviceList) { service ->
                        ServiceCard(
                            id = service.id,
                            description = service.description,
                            image = service.imageUrl,
                            value = service.value,
                            onClick = { id -> println("Clicked on service with id: $id") }
                        )
                    }
                }

                // Botões de paginação
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = { viewModel.previousPage() },
                        enabled = currentPage > 0,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = CustomBlue,
                            contentColor = Color.White
                        )
                    ) {
                        Text("Anterior")
                    }

                    Text(
                        text = "Página ${currentPage + 1} de $totalPages",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Button(
                        onClick = { viewModel.nextPage() },
                        enabled = (currentPage + 1) * 10 < viewModel.serviceList.size,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = CustomBlue,
                            contentColor = Color.White
                        )
                    ) {
                        Text("Próximo")
                    }
                }
            }
        }
    }
}