package com.connectdeaf.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.connectdeaf.ui.components.DrawerMenu
import com.connectdeaf.ui.components.DropdownMenuField
import com.connectdeaf.ui.components.SearchBarField
import com.connectdeaf.ui.components.ServiceCard
import com.connectdeaf.viewmodel.DrawerViewModel
import com.connectdeaf.ui.theme.AppStrings
import com.connectdeaf.viewmodel.ServicesViewModel
import com.connectdeaf.viewmodel.factory.ServicesViewModelFactory
import kotlinx.coroutines.launch

@Composable
fun ServicesScreen(
    navController: NavController,
    drawerViewModel: DrawerViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val context = LocalContext.current
    val viewModel: ServicesViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = ServicesViewModelFactory(context)
    )

    val isLoading = viewModel.isLoading.value
    val searchQuery = viewModel.searchQuery.value
    val serviceList = viewModel.getPaginatedList()
    val selectedState = viewModel.selectedState.value
    val selectedCity = viewModel.selectedCity.value

    val CustomBlue = Color(0xFF3D66CC)
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.loadStatesFromIBGE(context = context)
    }

    DrawerMenu(
        navController = navController,
        scope = scope,
        drawerViewModel = drawerViewModel,
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
                    .padding(16.dp),
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

                if (isLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = CustomBlue)
                    }
                } else {
                    // Search Bar
                    SearchBarField(
                        searchQuery = viewModel.searchQuery.collectAsState().value, // Passa o TextFieldValue completo
                        onSearchQueryChange = { newValue ->
                            viewModel.onSearchQueryChange(newValue) // Atualiza o TextFieldValue completo no ViewModel
                        },
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
                            value = selectedState,
                            label = AppStrings.ESTADO,
                            onValueChange = { newState ->
                                viewModel.updateState(newState)
                                viewModel.updateCity("") // Limpar a cidade ao selecionar um novo estado
                                viewModel.loadCitiesForStateFromIBGE(newState, context) // Carregar as cidades ao selecionar o estado
                            },
                            options = viewModel.states,
                            modifier = Modifier.weight(1f)
                        )

                        DropdownMenuField(
                            value = selectedCity,
                            label = AppStrings.CIDADE,
                            onValueChange = { viewModel.updateCity(it) },
                            options = viewModel.cities,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    // Lista de Serviços
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        items(serviceList) { service ->
                            ServiceCard(
                                id = service.id,
                                name = service.name,
                                description = service.description,
                                image = null,
                                value = service.value,
                                onClick = { navController.navigate("service/${service.id}") }
                            )
                        }
                    }

                    // Paginação
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = { viewModel.previousPage() },
                            enabled = viewModel.currentPage.value > 0
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Página anterior"
                            )
                        }
                        Text(
                            text = "Página ${viewModel.currentPage.value + 1}",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                        IconButton(
                            onClick = { viewModel.nextPage() },
                            enabled = (viewModel.currentPage.value + 1) * viewModel.servicesPerPage < viewModel.filteredServiceList.size
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowForward,
                                contentDescription = "Próxima página"
                            )
                        }
                    }
                }
            }
        }
    }
}
