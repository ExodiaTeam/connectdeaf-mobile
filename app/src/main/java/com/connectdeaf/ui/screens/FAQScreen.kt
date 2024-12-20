package com.connectdeaf.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.connectdeaf.ui.components.DrawerMenu
import com.connectdeaf.ui.components.MessageCard
import com.connectdeaf.ui.components.TopAppBar
import com.connectdeaf.viewmodel.DrawerViewModel
import com.connectdeaf.viewmodel.FAQViewModel
import kotlinx.coroutines.launch


@Composable
fun FAQScreen(
    faqViewModel: FAQViewModel? = null,
    navController: NavController,
    drawerViewModel: DrawerViewModel = viewModel()
) {
    var userMessage by remember { mutableStateOf(TextFieldValue("")) }

    val scope = rememberCoroutineScope()

    DrawerMenu(
        navController = navController,
        scope = scope,
        gesturesEnabled = false,
        drawerViewModel = drawerViewModel,
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    onOpenDrawerMenu = { scope.launch { drawerViewModel.openMenuDrawer() } },
                    onOpenDrawerNotifications = { scope.launch { drawerViewModel.openNotificationsDrawer() } },
                    showBackButton = true,
                    isBot = true,
                    navController = navController
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(MaterialTheme.colorScheme.background),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(16.dp)
                ) {
                    MessageCard(
                        sender = "CDBot",
                        message = "Oi! 👋 Eu sou o **CDBot**, seu bot de dúvidas aqui no app. 🤖\nEstou aqui para responder suas perguntas frequentes e te ajudar com tudo o que precisar. É só perguntar, e eu dou aquela mãozinha! ✌️✨",
                        isBot = true
                    )
                    MessageCard(
                        sender = "Você",
                        message = "O Que é a ConnectDeaf?",
                        isBot = false
                    )
                    MessageCard(
                        sender = "Você",
                        message = "A ConnectDeaf é uma plataforma que visa promover inclusão social, fazendo a ponte entre profssionais de diversas áreas e pessoas surdas.",
                        isBot = true
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF3D66CC))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(15.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        BasicTextField(
                            value = userMessage,
                            onValueChange = { userMessage = it },
                            modifier = Modifier
                                .weight(1f)
                                .background(Color.White, shape = MaterialTheme.shapes.medium)
                                .padding(horizontal = 16.dp, vertical = 12.dp),
                            decorationBox = { innerTextField ->
                                if (userMessage.text.isEmpty()) {
                                    Text(
                                        text = "Pergunte algo ao CDBot...",
                                        color = Color.Gray,
                                        fontSize = 14.sp
                                    )
                                }
                                innerTextField()
                            }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        IconButton(
                            onClick = {
                            }
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(Color(0xFFFF9919))
                                    .padding(10.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.Send,
                                    contentDescription = "Enviar mensagem",
                                    tint = Color.White,
                                    modifier = Modifier
                                        .size(30.dp)
                                        .rotate(-45f)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


