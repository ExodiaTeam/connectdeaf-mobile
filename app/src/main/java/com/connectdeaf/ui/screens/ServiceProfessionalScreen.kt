package com.connectdeaf.ui.screens

import android.widget.Toast
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.connectdeaf.R
import com.connectdeaf.data.repository.AuthRepository
import com.connectdeaf.data.repository.ServicesRepository
import com.connectdeaf.ui.components.ServiceCard
import com.connectdeaf.ui.theme.PrimaryColor
import com.connectdeaf.viewmodel.ServiceProfessionalViewModel
import com.connectdeaf.viewmodel.factory.ServicesByProfessionalViewModelFactory


@Composable
fun ServiceProfessionalScreen(
    navController: NavController
) {
    val context = LocalContext.current
    val servicesRepository = ServicesRepository(context.applicationContext)
    val servicesProfessionalViewModel: ServiceProfessionalViewModel = viewModel(
        factory = ServicesByProfessionalViewModelFactory(
            context.applicationContext,
            servicesRepository
        )
    )
    val uiState by servicesProfessionalViewModel.uiState.collectAsState()
    var serviceToDelete by remember { mutableStateOf<String?>(null) }
    val idProfessional = AuthRepository(context).getProfessionalId() ?: ""

    LaunchedEffect(idProfessional) {
        servicesProfessionalViewModel.getServicesByProfessional(idProfessional, context)
    }

    Scaffold(
        topBar = {
            com.connectdeaf.ui.components.TopAppBar(
                showBackButton = true,
                navController = navController
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
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
                        .padding(horizontal = 8.dp, vertical = 8.dp),
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
                            imageVector = Icons.Default.Add,
                            contentDescription = "Adicionar Serviço",
                            tint = Color.White
                        )
                        Text("Adicionar Serviço", fontSize = 16.sp, fontWeight = FontWeight.Bold)
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
                            name = service.name,
                            description = service.description,
                            image = R.drawable.doutor.toString(),
                            value = service.value,
                            isProfessional = true,
                            onClick = { },
                            onDeleteClick = { serviceId -> serviceToDelete = serviceId }
                        )
                    }
                }
            }

            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(50.dp),
                    color = PrimaryColor
                )
            }
        }

        // Confirmação de exclusão do serviço
        serviceToDelete?.let { id ->
            AlertDialog(
                onDismissRequest = { serviceToDelete = null },
                title = { Text("Confirmar exclusão") },
                text = { Text("Tem certeza que deseja excluir este serviço?") },
                confirmButton = {
                    TextButton(onClick = {
                        servicesProfessionalViewModel.deleteService(id, context)
                        Toast.makeText(context, "Serviço excluído com sucesso!", Toast.LENGTH_SHORT)
                            .show()
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
