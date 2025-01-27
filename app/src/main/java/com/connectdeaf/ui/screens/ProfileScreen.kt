package com.connectdeaf.ui.screens

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
import androidx.compose.ui.tooling.preview.*
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
    val scope = rememberCoroutineScope()

    val context = LocalContext.current

    val authRepository = AuthRepository(context)
    val professionalId = authRepository.getProfessionalId()

    LaunchedEffect (key1 = professionalId) {
        if (professionalId != null) {
            viewModel.fetchProfile(professionalId, context)
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
                // Título "Perfil do profissional"
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Perfil do profissional",
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
                // Header (Avatar + Name + Location)
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
                            profileState.value?.name?.let {
                                Text(
                                    text = it,
                                    style = MaterialTheme.typography.titleLarge,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                            Row {
                                Image(
                                    painter = painterResource(id = R.drawable.location_icon),
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "${profileState.value?.addresses?.first()?.city}, ${profileState.value?.addresses?.first()?.state}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Blue
                                )
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

                    // "Minhas habilidades"
                    if (profileState.value?.category?.isNotEmpty() == true) {
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
                                            ChipComponent(text = chipSkill)
                                        }
                                    }
                                    currentRowItems = mutableListOf()
                                    Spacer(modifier = Modifier.height(8.dp))
                                }
                            }
                        }

                    }
                }



                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Serviços e avaliações",
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
                // Serviços
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
                }else{
                    Text(
                        text = "Nenhum serviço cadastrado",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }


                // Avaliações

                SectionCard(title = "Avaliações sobre o profissional") {
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

                    }
                    else{
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
        ProfileScreen(navController = NavHostController(LocalContext.current))
    }
}
