package com.connectdeaf.ui.components

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
    navController: NavController? = null,
    showBackButton: Boolean = false
) {
    CenterAlignedTopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_horizontal),
                    contentDescription = "Logo Horizontal",
                    modifier = Modifier.size(166.dp)
                )
            }
        },
        navigationIcon = {
            if (showBackButton) {

                IconButton(onClick = { navController?.popBackStack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Voltar",
                        tint = Color.White
                    )
                }
            } else {

                IconButton(onClick = {
                    navController?.navigate("Notifications") {
                        launchSingleTop = true
                    }
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

            if (!showBackButton) {
                IconButton(onClick = {
                    navController?.navigate("Menu") {
                        launchSingleTop = true
                    }
                }) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menu",
                        tint = Color.White
                    )
                }
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color(0XFF478FCC)
        )
    )
}