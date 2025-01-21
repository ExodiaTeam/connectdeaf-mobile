package com.connectdeaf.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.connectdeaf.ui.screens.AddressScreen
import com.connectdeaf.ui.screens.FAQScreen
import com.connectdeaf.ui.screens.HomeScreen
import com.connectdeaf.ui.screens.ProfileScreen
import com.connectdeaf.ui.screens.RegisterInitialScreen
import com.connectdeaf.ui.screens.RegisterProfessionalScreen
import com.connectdeaf.ui.screens.RegisterScreen
import com.connectdeaf.ui.screens.ServicesScreen
import com.connectdeaf.ui.screens.SignInScreen
import com.connectdeaf.ui.screens.SuccessRegistrationScreen
import com.connectdeaf.viewmodel.RegisterViewModel
import com.connectdeaf.viewmodel.factory.RegisterViewModelFactory


@Composable
fun AppNavigation(navController: NavHostController) {
    val registerViewModel: RegisterViewModel = viewModel(factory = RegisterViewModelFactory())

    NavHost(
        navController = navController,
        startDestination = "registerInitialScreen" // Esta será a primeira tela a ser exibida
    ) {


        composable("loginScreen") { SignInScreen(navController = navController) }
        composable("registerInitialScreen") { RegisterInitialScreen(navController = navController) }
        composable("registerProfessionalScreen") { RegisterProfessionalScreen(navController = navController, onClick = {}) }
        composable("registerScreen") { RegisterScreen(navController = navController, registerViewModel = registerViewModel) }
        composable("addressScreen") { AddressScreen(navController = navController, registerViewModel = registerViewModel) } // Tela de Endereço
        composable("successRegistrationScreen") { SuccessRegistrationScreen(navController = navController) }
        composable("home") { HomeScreen(navController = navController) }
        composable("profile") { ProfileScreen(navController = navController) }
        composable("services") { ServicesScreen(navController = navController) }
        composable("faq") { FAQScreen(navController = navController) }
        composable("registerServiceScreen") { RegisterScreen(navController = navController, registerViewModel = registerViewModel) }

    }
}
