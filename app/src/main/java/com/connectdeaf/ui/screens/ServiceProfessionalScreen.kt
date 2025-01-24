package com.connectdeaf.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.connectdeaf.R
import com.connectdeaf.data.repository.AuthRepository
import com.connectdeaf.data.repository.ServicesRepository
import com.connectdeaf.ui.components.ServiceCard
import com.connectdeaf.ui.theme.PrimaryColor
import com.connectdeaf.viewmodel.DrawerViewModel
import com.connectdeaf.viewmodel.RegisterProfessionalViewModel
import com.connectdeaf.viewmodel.ServicesProfessionalViewModel
import kotlinx.coroutines.launch


@Composable
fun ServiceProfessionalScreen(
    servicesProfessionalViewModel: ServicesProfessionalViewModel = viewModel(),
    onEditService: (String) -> Unit,
    onDeleteService: (String) -> Unit,
    drawerViewModel: DrawerViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    navController: NavController
) {
    val uiState by servicesProfessionalViewModel.uiState.collectAsState()
    var serviceToDelete by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var idProfessional = AuthRepository(context).getProfessionalId() ?:""

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
                .padding(horizontal = 16.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Serviços",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            OutlinedButton(
                onClick = { navController.navigate("registerServiceScreen") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 8.dp,
                    vertical = 8.dp ), // Maior espaçamento entre o botão e os itens
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryColor,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(6.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add, // Ícone de "+"
                        contentDescription = "Adicionar Serviço",
                        tint = Color.White
                    )
                    Text(
                        "Adicionar Serviço",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(uiState.services) { service ->
                    ServiceCard(
                        id = service.id.toString(),
                        description = service.description,
                        image = R.drawable.doutor.toString(),
                        value = service.value.toString(),
                        isProfessional = false
                    )
                }
            }

        }

        serviceToDelete?.let { id ->
            AlertDialog(
                onDismissRequest = { serviceToDelete = null },
                title = { Text("Confirmar exclusão") },
                text = { Text("Tem certeza que deseja excluir este serviço?") },
                confirmButton = {
                    TextButton(onClick = {
                        onDeleteService(id)
                        serviceToDelete = null
                    }) {
                        Text("Excluir", color = MaterialTheme.colorScheme.error)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { serviceToDelete = null }) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ServiceProfessionalScreenPreview() {
    val serviceRepository = ServicesRepository()
    ServiceProfessionalScreen(
        servicesProfessionalViewModel = ServicesProfessionalViewModel(serviceRepository),
        onEditService = { id -> println("Editar Serviço: $id") },
        onDeleteService = { id -> println("Deletar Serviço: $id") },
        navController = NavController(LocalContext.current)
    )
}
