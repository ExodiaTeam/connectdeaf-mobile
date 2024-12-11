package com.connectdeaf.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.connectdeaf.ui.screens.HomeScreen
import com.connectdeaf.ui.screens.ProfileScreen
import com.connectdeaf.ui.screens.RegisterScreenPreview
import com.connectdeaf.ui.screens.ServicesScreen


@Composable
fun AppNavigation(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = "register",
        modifier = modifier
    ) {
        composable("home") { HomeScreen() }
        composable("register") { RegisterScreenPreview() }
        composable("services") { ServicesScreen() }
        composable("profile") { ProfileScreen() }
    }
}