package com.connectdeaf.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.connectdeaf.data.repository.AuthRepository
import com.connectdeaf.ui.components.DrawerMenu
import com.connectdeaf.ui.components.ScheduleCard
import com.connectdeaf.viewmodel.DrawerViewModel
import com.connectdeaf.viewmodel.ScheduleViewModel
import kotlinx.coroutines.launch


@Composable
fun ScheduleScreen(
    navController: NavController,
    drawerViewModel: DrawerViewModel,
    scheduleViewModel: ScheduleViewModel = viewModel(),
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val authRepository = remember { AuthRepository(context) }

    val role = remember { authRepository.getRoles() }

    val schedule = scheduleViewModel.scheduleItems.value
    val appointments by scheduleViewModel.appointments
    val searchQuery by scheduleViewModel.searchQuery
    val isLoading by scheduleViewModel.isLoading


    if (role != null) {
        if (role.contains("ROLE_USER")) {
            val userId = remember { authRepository.getUserId() }
            LaunchedEffect(userId) {
                userId?.let {
                    scheduleViewModel.fetchCustomerAppointments(it, context)
                }
            }
        } else if(role.contains("ROLE_PROFESSIONAL")) {
            val professionalId = remember { authRepository.getProfessionalId() }
            LaunchedEffect(professionalId) {
                professionalId?.let {
                    scheduleViewModel.fetchProfessionalAppointments(it, context)
                }
            }
        }
    }

    Log.d("role", role.toString())

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
                    navController = navController
                )
            }
        ) { paddingValues ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { scheduleViewModel.onSearchQueryChanged(it) },
                        placeholder = {
                            Text(
                                "Pesquisar por serviço, prestador...",
                                color = Color.Gray,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = {
                            Icon(Icons.Default.Search, contentDescription = "Search Icon")
                        },
                        shape = RoundedCornerShape(8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (isLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else {
                    if (appointments.isNotEmpty()) {
                        // Lista de agendamentos
                        LazyColumn(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(schedule) { schedule ->
                                ScheduleCard(
                                    navController,
                                    schedule,
                                    role.toString(),
                                    scheduleViewModel,
                                    drawerViewModel.notificationViewModel
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Nenhum agendamento encontrado",
                                color = Color.Gray,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}
