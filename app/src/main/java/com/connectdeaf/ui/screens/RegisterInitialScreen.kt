package com.connectdeaf.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.connectdeaf.R
import com.connectdeaf.ui.components.DrawerMenu
import com.connectdeaf.ui.components.OptionCardButton
import com.connectdeaf.ui.components.TopAppBar
import kotlinx.coroutines.launch

@Composable
fun RegisterInitialScreen(navController: NavHostController) {
    val drawerStateMenu = rememberDrawerState(DrawerValue.Closed)
    val drawerStateNotifications = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val selectedOption = remember { mutableStateOf<String?>(null) }

    DrawerMenu(
        navController = navController,
        scope = scope,
        drawerStateMenu = drawerStateMenu,
        drawerStateNotifications = drawerStateNotifications,
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    onOpenDrawerMenu = { scope.launch { drawerStateMenu.open() } },
                    onOpenDrawerNotifications = { scope.launch { drawerStateNotifications.open() } },
                    showBackButton = true,
                    isBot = false,
                    isRegistration = true
                )
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color(0xFFF5F5F5)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Bem vindo(a) à",
                        fontSize = 20.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = "ConnectDeaf!",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF478FCC)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Vamos começar? Primeiramente, me diz, você é um...",
                        fontSize = 16.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    OptionCardButton(
                        text = "Cliente",
                        isSelected = selectedOption.value == "Cliente",
                        onClick = { selectedOption.value = "Cliente" },
                        imageResource = R.drawable.user_button
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OptionCardButton(
                        text = "Profissional",
                        isSelected = selectedOption.value == "Profissional",
                        onClick = { selectedOption.value = "Profissional" },
                        imageResource = R.drawable.customer_button
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = { navController.navigate("nextScreen") },
                        enabled = selectedOption.value != null,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(text = "CONTINUAR")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Já tenho uma conta.",
                        fontSize = 14.sp,
                        color = Color(0xFF50A1E6),
                        textDecoration = TextDecoration.Underline,
                        modifier = Modifier
                            .clickable {
                                navController.navigate("loginScreen")
                            }
                    )

                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewRegisterInitialScreen() {
    val navController = rememberNavController()
    RegisterInitialScreen(navController = navController)
}

