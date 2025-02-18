package com.connectdeaf.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.connectdeaf.ui.screens.AddressScreen
import com.connectdeaf.ui.screens.chat.ChatListScreen
import com.connectdeaf.ui.screens.chat.ChatScreen
import com.connectdeaf.ui.screens.FAQScreen
import com.connectdeaf.ui.screens.HomeScreen
import com.connectdeaf.ui.screens.ProfileScreen
import com.connectdeaf.ui.screens.RegisterInitialScreen
import com.connectdeaf.ui.screens.RegisterProfessionalScreen
import com.connectdeaf.ui.screens.RegisterScreen
import com.connectdeaf.ui.screens.ScheduleScreen
import com.connectdeaf.ui.screens.AppointmentScreen
import com.connectdeaf.ui.screens.RegisterServiceScreen
import com.connectdeaf.ui.screens.ServiceProfessionalScreen
import com.connectdeaf.ui.screens.ServiceScreen
import com.connectdeaf.ui.screens.ServicesScreen
import com.connectdeaf.ui.screens.SignInScreen
import com.connectdeaf.ui.screens.SuccessRegistrationScreen
import com.connectdeaf.viewmodel.NotificationViewModel
import com.connectdeaf.viewmodel.RegisterViewModel
import com.connectdeaf.viewmodel.ServicesViewModel
import com.connectdeaf.viewmodel.factory.RegisterViewModelFactory
import com.connectdeaf.viewmodel.factory.ServicesViewModelFactory


@Composable
fun AppNavigation(navController: NavHostController) {
    val registerViewModel: RegisterViewModel = viewModel(factory = RegisterViewModelFactory())
    val servicesViewModel: ServicesViewModel = viewModel(factory = ServicesViewModelFactory(navController.context))
    val drawerViewModel = viewModel<com.connectdeaf.viewmodel.DrawerViewModel>()

    NavHost(
        navController = navController,
        startDestination = "registerInitialScreen" // Esta será a primeira tela a ser exibida
    ) {

        composable(
            route = "appointmentScreen/{serviceId}/{professionalId}/{value}",
            arguments = listOf(
                navArgument("serviceId") { type = NavType.StringType },
                navArgument("professionalId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val serviceId = backStackEntry.arguments?.getString("serviceId") ?: ""
            val professionalId = backStackEntry.arguments?.getString("professionalId") ?: ""
            val value = backStackEntry.arguments?.getString("value") ?: ""
            AppointmentScreen(navController = navController, serviceId = serviceId, professionalId = professionalId, value = value)
        }
        composable("loginScreen") { SignInScreen(navController = navController) }
        composable("ScheduleScreen") { ScheduleScreen(navController = navController, drawerViewModel = drawerViewModel) }
        composable("registerInitialScreen") { RegisterInitialScreen(navController = navController, registerViewModel = registerViewModel) }
        composable("registerProfessionalScreen") { RegisterProfessionalScreen(navController = navController, registerViewModel = registerViewModel) }
        composable("registerScreen") { RegisterScreen(navController = navController, registerViewModel = registerViewModel) }
        composable("addressScreen") { AddressScreen(navController = navController, registerViewModel = registerViewModel) } // Tela de Endereço
        composable("successRegistrationScreen") { SuccessRegistrationScreen(navController = navController) }
        composable("home") { HomeScreen(navController = navController, drawerViewModel = drawerViewModel, servicesViewModel = servicesViewModel) }
        composable("profile") { ProfileScreen(navController = navController) }
        composable("services") { ServicesScreen(navController = navController, servicesViewModel = servicesViewModel) }
        composable("faq") { FAQScreen(navController = navController) }
        composable(
            route = "service/{serviceId}",
            arguments = listOf(
                navArgument("serviceId") {
                    type = NavType.StringType // Define o tipo do argumento
                    nullable = false          // Define se o argumento pode ser nulo
                }
            )
        ) { backStackEntry ->
            // Pegando o parâmetro passado via rota
            val serviceId = backStackEntry.arguments?.getString("serviceId")
            ServiceScreen(navController = navController, serviceId = serviceId.orEmpty())
        }
        composable(
            route = "service/{serviceId}",
            arguments = listOf(
                navArgument("serviceId") {
                    type = NavType.StringType // Define o tipo do argumento
                    nullable = false          // Define se o argumento pode ser nulo
                }
                )
        ) { backStackEntry ->
            // Pegando o parâmetro passado via rota
            val serviceId = backStackEntry.arguments?.getString("serviceId")
            ServiceScreen(navController = navController, serviceId = serviceId.orEmpty())
        }
        composable("serviceProfessionalScreen") {
            ServiceProfessionalScreen(
                navController = navController,
                drawerViewModel = drawerViewModel
            )
        }
        composable("registerServiceScreen") {
            RegisterServiceScreen(
                navController = navController
            )
        }



        composable("chatList") { ChatListScreen(navController) }
        composable(
            route = "chat/{id}",
            arguments = listOf(
                navArgument(name = "id") {
                    type = NavType.StringType
                },
            )
        ) { backstackEntry ->
            val id = backstackEntry.arguments?.getString("id")
            if (id != null) {
                ChatScreen(navController, id = id)
            }
        }
    }
}
