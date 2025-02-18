package com.connectdeaf.ui.screens

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.connectdeaf.R
import com.connectdeaf.data.repository.AuthRepository
import com.connectdeaf.ui.components.AssessmentCard
import com.connectdeaf.ui.components.ChipComponent
import com.connectdeaf.ui.components.DrawerMenu
import com.connectdeaf.ui.components.MonthlyRevenueChart
import com.connectdeaf.ui.components.ServiceCard
import com.connectdeaf.ui.theme.ConnectDeafTheme
import com.connectdeaf.viewmodel.DrawerViewModel
import com.connectdeaf.viewmodel.ProfileViewModel
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = viewModel(),
    navController: NavController,
    drawerViewModel: DrawerViewModel = viewModel()
) {
    // Observa o perfil
    val profileState = viewModel.profile.collectAsState()
    // Observa os agendamentos (caso profissional)
    val appointments = viewModel.appointments.collectAsState().value
    // Observa o estado do documento (upload/verificação)
    val documentState by viewModel.documentState.collectAsState()

    // Contexto e escopo de corrotina
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // Repositório de auth (para saber se é cliente ou profissional)
    val authRepository = AuthRepository(context)
    val role = authRepository.getRoles()?.firstOrNull() ?: ""
    val userId = authRepository.getUserId() ?: ""
    val professionalId = authRepository.getProfessionalId() ?: ""

    Log.d("ROLE", "$role")

    // Busca os dados de perfil ao montar a tela
    LaunchedEffect(userId, role) {
        if (role == "ROLE_PROFESSIONAL") {
            viewModel.fetchProfile(professionalId, role, context)
            viewModel.fetchAppointments(professionalId, context)
        } else {
            viewModel.fetchProfile(userId, role, context)
        }
    }

    // Menu lateral e Notifications
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
                    showBackButton = true,
                    navController = navController
                )
            }
        ) { paddingValues ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(paddingValues)
                    .padding(16.dp)
                    .background(MaterialTheme.colorScheme.background),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Título da tela
                val profileTitle =
                    if (role == "ROLE_PROFESSIONAL") "Perfil do profissional" else "Perfil do cliente"
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = profileTitle,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }

                // ==================== HEADER ====================
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(4.dp),
                    elevation = CardDefaults.cardElevation(4.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Foto de perfil (ou placeholder)
                        AsyncImage(
                            model = profileState.value?.imageUrl,
                            contentDescription = "Profile Picture",
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                                .background(Color.Gray)
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            // Nome
                            if (role == "ROLE_PROFESSIONAL") {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    profileState.value?.name?.let {
                                        Text(
                                            text = it,
                                            style = MaterialTheme.typography.titleLarge,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }

                                    if (documentState.isVerified) {
                                        Spacer(modifier = Modifier.width(4.dp)) // Pequeno espaçamento entre nome e selo

                                        Icon(
                                            imageVector = Icons.Default.CheckCircle,
                                            contentDescription = "Selo de verificação",
                                            tint = Color.Blue, // Cor azul para destacar
                                            modifier = Modifier.size(24.dp) // Ajuste do tamanho do selo
                                        )
                                    }
                                }
                            } else {
                                profileState.value?.user?.name?.let {
                                    Text(
                                        text = it,
                                        style = MaterialTheme.typography.titleLarge,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }

                            // Localização
                            Row {
                                Image(
                                    painter = painterResource(id = R.drawable.location_icon),
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                if (role == "ROLE_PROFESSIONAL") {
                                    val address = profileState.value?.addresses?.firstOrNull()
                                    val city = address?.city ?: ""
                                    val state = address?.state ?: ""
                                    Text(
                                        text = "$city, $state",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = Color.Blue
                                    )
                                } else {
                                    val address = profileState.value?.user?.addresses?.firstOrNull()
                                    val city = address?.city ?: ""
                                    val state = address?.state ?: ""
                                    Text(
                                        text = "$city, $state",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = Color.Blue
                                    )
                                }
                            }

                            // Número de avaliações
                            Row {
                                Image(
                                    painter = painterResource(id = R.drawable.star_filled_icon),
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = " ${profileState.value?.assessments?.size ?: 0} " +
                                            "(${profileState.value?.assessments?.size ?: 0} avaliações)",
                                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 14.sp),
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                }

                // ==================== "MAIS SOBRE MIM" ====================
                SectionCard(title = "Mais sobre mim") {
                    profileState.value?.description?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Se for profissional, mostra habilidades
                    if (role == "ROLE_PROFESSIONAL" && !profileState.value?.category.isNullOrEmpty()) {
                        Column {
                            Text(
                                text = "Minhas habilidades",
                                style = MaterialTheme.typography.titleMedium
                            )
                            var currentRowItems = mutableListOf<String>()
                            profileState.value?.category?.forEachIndexed { index, skill ->
                                currentRowItems.add(skill)
                                if (currentRowItems.size == 3 || index == profileState.value?.category!!.lastIndex) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        currentRowItems.forEach { chipSkill ->
                                            ChipComponent(text = chipSkill, onClick = {})
                                        }
                                    }
                                    currentRowItems = mutableListOf()
                                    Spacer(modifier = Modifier.height(8.dp))
                                }
                            }
                        }
                    }
                }

                // ==================== "SERVIÇOS E AVALIAÇÕES" ====================
                val servicesAndAssessmentsTitle =
                    if (role == "ROLE_PROFESSIONAL") "Serviços e avaliações" else "Avaliações"
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = servicesAndAssessmentsTitle,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }

                // Mostra serviços apenas se for profissional
                if (role == "ROLE_PROFESSIONAL") {
                    if (!profileState.value?.services.isNullOrEmpty()) {
                        Row(
                            modifier = Modifier.horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            profileState.value?.services!!.forEach { service ->
                                ServiceCard(
                                    id = service.id,
                                    name = service.name,
                                    description = service.description,
                                    image = R.drawable.doutor.toString(),
                                    value = service.value,
                                    onClick = {}
                                )
                            }
                        }
                    } else {
                        Text(
                            text = "Nenhum serviço cadastrado",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Gráfico de desempenho se for profissional
                if (role == "ROLE_PROFESSIONAL") {
                    MonthlyRevenueChart(appointments = appointments)
                }

                // ==================== AVALIAÇÕES ====================
                SectionCard(title = if (role == "ROLE_PROFESSIONAL") "Avaliações sobre o profissional" else "Avaliações") {
                    if (!profileState.value?.assessments.isNullOrEmpty()) {
                        Row {
                            Image(
                                painter = painterResource(id = R.drawable.star_filled_icon),
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = " ${profileState.value?.assessments!!.size} " +
                                        "(${profileState.value?.assessments!!.size} avaliações)",
                                style = MaterialTheme.typography.bodySmall.copy(fontSize = 14.sp),
                                color = Color.Gray
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            modifier = Modifier.horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            profileState.value?.assessments!!.forEach { assessment ->
                                AssessmentCard(
                                    name = assessment.userName,
                                    stars = assessment.rating,
                                    description = assessment.text
                                )
                            }
                        }
                    } else {
                        Text(
                            text = "Nenhuma avaliação cadastrada",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                // ==================== CERTIFICADO (PARA PROFISSIONAIS) ====================
                if (role == "ROLE_PROFESSIONAL") {
                    Spacer(modifier = Modifier.height(16.dp))
                    SectionCard(title = "Certificado do Profissional") {
                        // Lançador de arquivos
                        val filePickerLauncher = rememberLauncherForActivityResult(
                            contract = ActivityResultContracts.GetContent()
                        ) { uri: Uri? ->
                            if (uri != null) {
                                viewModel.uploadAndVerifyCertificate(context, uri)
                            }
                        }

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            // Feedback visual sobre o status do certificado
                            if (documentState.isVerified) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                )
                                {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = "Certificado verificado",
                                        tint = Color.Green,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Text(
                                        text = "Certificado verificado",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = Color.Green
                                    )
                                }
                            } else {
                                Text(
                                    text = "Nenhum certificado carregado",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Gray
                                )
                            }

                            // Botão de upload
                            Button(
                                onClick = { filePickerLauncher.launch("*/*") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp),
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primary,
                                    contentColor = Color.White
                                )
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                )
                                {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_upload),
                                        contentDescription = "Ícone de upload",
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Text(
                                        text = "Adicionar Certificado",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                        }
                    }
                }

            }
        }
    }
}

@Composable
fun SectionCard(title: String, content: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(4.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
            content()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProfileScreen() {
    ConnectDeafTheme {
        ProfileScreen(
            navController = NavHostController(LocalContext.current)
        )
    }
}
