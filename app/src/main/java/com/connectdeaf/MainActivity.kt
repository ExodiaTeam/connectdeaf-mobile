package com.connectdeaf

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.connectdeaf.navigation.AppNavigation
import com.connectdeaf.ui.components.MenuDrawerContent
import com.connectdeaf.ui.components.NotificationDrawerContent
import com.connectdeaf.ui.components.TopAppBar
import com.connectdeaf.ui.screens.RegisterScreen
import com.connectdeaf.ui.theme.ConnectDeafTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val navController = rememberNavController()
            val drawerStateMenu = rememberDrawerState(DrawerValue.Closed)
            val drawerStateNotifications = rememberDrawerState(DrawerValue.Closed)
            val scope = rememberCoroutineScope()

            enableEdgeToEdge()
            ConnectDeafTheme {

                ModalNavigationDrawer(
                    drawerState = drawerStateMenu,
                    gesturesEnabled = true,
                    drawerContent = {
                        MenuDrawerContent(
                            navController = navController,
                            drawerState = drawerStateMenu,
                            scope = scope,
                        )
                    },
                    content = {
                        ModalNavigationDrawer(
                            drawerState = drawerStateNotifications,
                            gesturesEnabled = true,
                            drawerContent = {
                                NotificationDrawerContent(
                                    navController = navController,
                                    drawerState = drawerStateNotifications,
                                    scope = scope
                                )
                            },
                            content = {
                                Scaffold(
                                    topBar = {
                                        TopAppBar(
                                            onOpenDrawerMenu = { scope.launch { drawerStateMenu.open() } },
                                            onOpenDrawerNotifications = { scope.launch { drawerStateNotifications.open() } }
                                        )
                                    }
                                ) { innerPadding ->
                                    AppNavigation(
                                        navController = navController,
                                        modifier = Modifier.padding(innerPadding)
                                    )
                                }
                            }
                        )
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun RegisterScreenPreview() {
    RegisterScreen(onClick = {})
}




