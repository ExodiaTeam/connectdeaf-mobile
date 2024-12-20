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
import com.connectdeaf.ui.components.AssessmentCard
import com.connectdeaf.ui.components.ChipComponent
import com.connectdeaf.ui.components.DrawerMenu
import com.connectdeaf.ui.components.ServiceCard
import com.connectdeaf.ui.theme.ConnectDeafTheme
import com.connectdeaf.viewmodel.Assessment
import com.connectdeaf.viewmodel.DrawerViewModel
import com.connectdeaf.viewmodel.Profile
import com.connectdeaf.viewmodel.ProfileViewModel
import com.connectdeaf.viewmodel.Service
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    profile: Profile? = fakeProfile,
    viewModel: ProfileViewModel = viewModel(),
    navController: NavController,
    drawerViewModel: DrawerViewModel = viewModel()
) {


    if (profile == null) {
        CircularProgressIndicator(modifier = Modifier.fillMaxSize())
        return
    }

    val scope = rememberCoroutineScope()

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
                            model = profile.imageUrl,
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
                            Text(
                                text = profile.name,
                                style = MaterialTheme.typography.titleLarge,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Row {
                                Image(
                                    painter = painterResource(id = R.drawable.location_icon),
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "${profile.city}, ${profile.state}",
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
                                    text = " ${profile.assessments.size} (${profile.assessments.size} avaliações)",
                                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 14.sp),
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                }

                // "Mais sobre mim"
                SectionCard(title = "Mais sobre mim") {
                    Text(
                        text = profile.description,
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // "Minhas habilidades"
                    if (profile.category.isNotEmpty()) {
                        Column {
                            Text(
                                text = "Minhas habilidades",
                                style = MaterialTheme.typography.titleMedium
                            )
                            var currentRowItems = mutableListOf<String>()
                            profile.category.forEachIndexed { index, skill ->
                                currentRowItems.add(skill)
                                if (currentRowItems.size == 3 || index == profile.category.lastIndex) {
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
                if (profile.services.isNotEmpty()) {
                    Row(
                        modifier = Modifier
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        profile.services.forEach { service ->
                            ServiceCard(
                                id = service.id,
                                description = service.description,
                                image = R.drawable.doutor.toString(),
                                value = service.value,
                                onClick = {}
                            )
                        }
                    }
                }


                // Avaliações
                if (profile.assessments.isNotEmpty()) {
                    SectionCard(title = "Avaliações sobre o profissional") {
                        Row {
                            Image(
                                painter = painterResource(id = R.drawable.star_filled_icon),
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = " ${profile.assessments.size} (${profile.assessments.size} avaliações)",
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
                            profile.assessments.forEach { assessment ->
                                AssessmentCard(
                                    name = assessment.name,
                                    stars = assessment.stars,
                                    description = assessment.description
                                )
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

val fakeServices = listOf(
    Service(
        id = "1",
        name = "Desenvolvimento de Aplicativos",
        description = "Criação de aplicativos personalizados para Android e iOS.",
        category = listOf("Tecnologia", "Desenvolvimento", "Mobile"),
        value = "R$ 500,00",
        imageUrl = "https://via.placeholder.com/150"
    ),
    Service(
        id = "2",
        name = "Consultoria em Tecnologia",
        description = "Consultoria para melhorar processos e implementar soluções tecnológicas.",
        category = listOf("Consultoria", "TI"),
        value = "R$ 300,00",
        imageUrl = "https://via.placeholder.com/150"
    )
)

val fakeAssessments = listOf(
    Assessment(
        name = "Maria Oliveira",
        stars = 5,
        description = "Ótimo serviço! Muito profissional e eficiente."
    ),
    Assessment(
        name = "Carlos Andrade",
        stars = 4,
        description = "Muito bom, mas houve um pequeno atraso na entrega."
    )
)

val fakeProfile = Profile(
    id = "1",
    name = "João da Silva",
    imageUrl = "https://via.placeholder.com/64",
    city = "Quixadá",
    state = "CE",
    description = "Sou um desenvolvedor Android apaixonado por Compose.",
    category = listOf("Kotlin", "Compose", "Android"),
    services = fakeServices,
    assessments = fakeAssessments,
)

@Composable
@Preview(showBackground = true)
fun PreviewProfileScreen() {
    ConnectDeafTheme {
        ProfileScreen(profile = fakeProfile, navController = NavHostController(LocalContext.current))
    }
}