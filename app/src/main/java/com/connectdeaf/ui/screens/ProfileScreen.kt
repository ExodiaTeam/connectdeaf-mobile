package com.connectdeaf.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
    val profileState = viewModel.profile.collectAsState()
    val appointments = viewModel.appointments.collectAsState().value
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val authRepository = AuthRepository(context)
    val role = authRepository.getRoles()?.firstOrNull() ?: ""
    val userId = authRepository.getUserId() ?: ""
    val professionalId = authRepository.getProfessionalId() ?: ""

    Log.d("ROLE", "${role}")


    // Chama o fetch com userId e role
    LaunchedEffect(userId, role) {
        if (role == "ROLE_PROFESSIONAL") {
            viewModel.fetchProfile(professionalId, role, context)
            viewModel.fetchAppointments(professionalId, context)
        } else {
            viewModel.fetchProfile(userId, role, context)
        }
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
                // Título da tela (varia de acordo com o tipo de usuário)
                val profileTitle = if (role == "ROLE_PROFESSIONAL") "Perfil do profissional" else "Perfil do cliente"
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

                // Header (Avatar + Nome + Localização)
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
                            if(role == "ROLE_PROFESSIONAL"){
                                profileState.value?.name.let {
                                    if (it != null) {
                                        Text(
                                            text = it,
                                            style = MaterialTheme.typography.titleLarge,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                }
                            }else{
                                profileState.value?.user?.name.let {
                                    if (it != null) {
                                        Text(
                                            text = it,
                                            style = MaterialTheme.typography.titleLarge,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                }
                            }

                            Row {
                                Image(
                                    painter = painterResource(id = R.drawable.location_icon),
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                if(role == "ROLE_PROFESSIONAL"){
                                    Text(
                                        text = "${profileState.value?.addresses?.first()?.city}, ${profileState.value?.addresses?.first()?.state}",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = Color.Blue
                                    )
                                }else{
                                    Text(
                                        text = "${profileState.value?.user?.addresses?.first()?.city}, ${profileState.value?.user?.addresses?.first()?.state}",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = Color.Blue
                                    )
                                }

                            }
                            Row {
                                Image(
                                    painter = painterResource(id = R.drawable.star_filled_icon),
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = " ${profileState.value?.assessments?.size} (${profileState.value?.assessments?.size} avaliações)",
                                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 14.sp),
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                }

                // "Mais sobre mim"
                SectionCard(title = "Mais sobre mim") {
                    profileState.value?.description?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // "Minhas habilidades" exibido apenas para profissionais
                    if (role == "ROLE_PROFESSIONAL" && profileState.value?.category?.isNotEmpty() == true) {
                        Column {
                            Text(
                                text = "Minhas habilidades",
                                style = MaterialTheme.typography.titleMedium
                            )
                            var currentRowItems = mutableListOf<String>()
                            profileState.value?.category!!.forEachIndexed { index, skill ->
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

                // Seção de "Serviços e avaliações" para profissional ou apenas "Avaliações" para cliente
                val servicesAndAssessmentsTitle = if (role == "ROLE_PROFESSIONAL") "Serviços e avaliações" else "Avaliações"
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
                // Exibe os serviços apenas para profissionais
                if (role == "ROLE_PROFESSIONAL") {
                    if (profileState.value?.services?.isNotEmpty() == true) {
                        Row(
                            modifier = Modifier
                                .horizontalScroll(rememberScrollState()),
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

                if (role == "ROLE_PROFESSIONAL") {
                    // Gráfico de desempenho
                    MonthlyRevenueChart(appointments = appointments)
                }
                // Avaliações (o título muda conforme o tipo de perfil)
                SectionCard(title = if (role == "ROLE_PROFESSIONAL") "Avaliações sobre o profissional" else "Avaliações") {
                    if (profileState.value?.assessments?.isNotEmpty() == true) {
                        Row {
                            Image(
                                painter = painterResource(id = R.drawable.star_filled_icon),
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = " ${profileState.value?.assessments!!.size} (${profileState.value?.assessments!!.size} avaliações)",
                                style = MaterialTheme.typography.bodySmall.copy(fontSize = 14.sp),
                                color = Color.Gray
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            modifier = Modifier
                                .horizontalScroll(rememberScrollState()),
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
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
            content()
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewProfileScreen() {
    ConnectDeafTheme {
        ProfileScreen(
            navController = NavHostController(LocalContext.current)
        )
    }
}
