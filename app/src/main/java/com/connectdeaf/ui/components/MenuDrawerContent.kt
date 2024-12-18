package com.connectdeaf.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun MenuDrawerContent(
    navController: NavController,
    drawerState: DrawerState,
    scope: CoroutineScope
) {
    Surface(
        modifier = Modifier
            .fillMaxHeight()
            .width(380.dp)
            .padding(end = 20.dp)
            .background(Color(0XFF478FCC)),
        color = Color(0XFF3D66CC)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Menu",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .padding(bottom = 12.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        scope.launch {
                            navController.navigate("home") {
                                launchSingleTop = true
                            }
                            drawerState.close()
                        }
                    }
                    .padding(vertical = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home",
                    modifier = Modifier.padding(end = 24.dp),
                    tint = Color.White
                )
                Text(
                    text = "Home",
                    color = Color.White
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        scope.launch {
                            navController.navigate("booking")
                            drawerState.close()
                        }
                    }
                    .padding(vertical = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Agendamento",
                    modifier = Modifier.padding(end = 24.dp),
                    tint = Color.White
                )
                Text(
                    text = "Agendamento",
                    color = Color.White
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        scope.launch {
                            navController.navigate("services") {
                                launchSingleTop = true
                            }
                            drawerState.close()
                        }
                    }
                    .padding(vertical = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Build,
                    contentDescription = "Serviços",
                    modifier = Modifier.padding(end = 24.dp),
                    tint = Color.White
                )
                Text(
                    text = "Serviços",
                    color = Color.White
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        scope.launch {
                            navController.navigate("profile") {
                                launchSingleTop = true
                            }
                            drawerState.close()
                        }
                    }
                    .padding(vertical = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Perfil",
                    modifier = Modifier.padding(end = 24.dp),
                    tint = Color.White
                )
                Text(
                    text = "Perfil",
                    color = Color.White
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        scope.launch {
                            navController.navigate("faq") {
                                launchSingleTop = true
                            }
                            drawerState.close()
                        }
                    }
                    .padding(vertical = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "FAQ",
                    modifier = Modifier.padding(end = 24.dp),
                    tint = Color.White
                )
                Text(
                    text = "FAQ",
                    color = Color.White
                )
            }
        }
    }
}