package com.connectdeaf.ui.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.connectdeaf.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    navController: NavController,
    onOpenDrawerNotifications: () -> Unit,
    onOpenDrawerMenu: () -> Unit,
    showBackButton: Boolean = false,
    isBot: Boolean = false,
    isRegistration: Boolean = false,
) {
    CenterAlignedTopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                val logoId = if (isBot) R.drawable.logo_bot else R.drawable.logo_horizontal
                Image(
                    painter = painterResource(id = logoId),
                    contentDescription = if (isBot) "Logo Bot" else "Logo Horizontal",
                    modifier = Modifier.size(if (isBot) 100.dp else 166.dp)
                )
            }
        },
        navigationIcon = {
            if (showBackButton) {
                IconButton(onClick = { navController.popBackStack()  }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Voltar",
                        tint = Color.White
                    )
                }
            } else if (!isRegistration) {
                IconButton(onClick = {
                    onOpenDrawerNotifications()
                }) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Notificação",
                        tint = Color.White
                    )
                }
            }
        },
        actions = {
            if (!showBackButton && !isRegistration) {
                IconButton(
                    onClick =
                    onOpenDrawerMenu
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menu",
                        tint = Color.White
                    )
                }
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color(0XFF3D66CC)
        )
    )
}