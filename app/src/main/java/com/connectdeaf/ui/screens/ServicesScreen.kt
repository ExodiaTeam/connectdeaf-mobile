package com.connectdeaf.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.connectdeaf.ui.components.DrawerMenu
import com.connectdeaf.ui.components.DropdownMenuField
import com.connectdeaf.ui.components.SearchBarField
import com.connectdeaf.ui.components.ServiceCard
import com.connectdeaf.ui.theme.AppStrings
import com.connectdeaf.viewmodel.DrawerViewModel
import com.connectdeaf.viewmodel.ServicesViewModel
import kotlinx.coroutines.launch

@Composable
fun ServicesScreen(
    navController: NavController,
    drawerViewModel: DrawerViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    servicesViewModel: ServicesViewModel
) {
    val context = LocalContext.current


    val isLoading = servicesViewModel.isLoading.value
    val serviceList = servicesViewModel.getPaginatedList()
    val selectedState = servicesViewModel.selectedState.value
    val selectedCity = servicesViewModel.selectedCity.value

    val CustomBlue = Color(0xFF3D66CC)
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        servicesViewModel.loadStatesFromIBGE(context = context)
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
                        searchQuery = servicesViewModel.searchQuery.collectAsState().value, // Passa o TextFieldValue completo
                        onSearchQueryChange = { newValue ->
                            servicesViewModel.onSearchQueryChange(newValue) // Atualiza o TextFieldValue completo no ViewModel
                        },
                        placeholder = "Pesquisar por serviço...",
                        modifier = Modifier.padding(bottom = 16.dp).fillMaxWidth()
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
                                servicesViewModel.updateState(newState)
                                servicesViewModel.updateCity("") // Limpar a cidade ao selecionar um novo estado
                                servicesViewModel.loadCitiesForStateFromIBGE(newState, context) // Carregar as cidades ao selecionar o estado
                            },
                            options = servicesViewModel.states,
                            modifier = Modifier.weight(1f)
                        )

                        DropdownMenuField(
                            value = selectedCity,
                            label = AppStrings.CIDADE,
                            onValueChange = { servicesViewModel.updateCity(it) },
                            options = servicesViewModel.cities,
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
                            onClick = { servicesViewModel.previousPage() },
                            enabled = servicesViewModel.currentPage.value > 0
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Página anterior"
                            )
                        }
                        Text(
                            text = "Página ${servicesViewModel.currentPage.value + 1}",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                        IconButton(
                            onClick = { servicesViewModel.nextPage() },
                            enabled = (servicesViewModel.currentPage.value + 1) * servicesViewModel.servicesPerPage < servicesViewModel.filteredServiceList.size
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
