package com.connectdeaf.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.connectdeaf.R
import com.connectdeaf.ui.components.DrawerMenu
import com.connectdeaf.ui.theme.GreyLighter
import com.connectdeaf.ui.theme.PrimaryColor
import kotlinx.coroutines.launch

@Composable
fun SuccessRegistrationScreen(onContinueClick: () -> Unit, navController: NavController) {
    val drawerStateMenu = rememberDrawerState(DrawerValue.Closed)
    val drawerStateNotifications = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    DrawerMenu(
        navController = navController,
        scope = scope,
        drawerStateMenu = drawerStateMenu,
        drawerStateNotifications = drawerStateNotifications
    ) {
        Scaffold(
            topBar = {
                com.connectdeaf.ui.components.TopAppBar(
                    onOpenDrawerMenu = { scope.launch { drawerStateMenu.open() } },
                    onOpenDrawerNotifications = { scope.launch { drawerStateNotifications.open() } },
                    showBackButton = true,
                    navController = navController
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(32.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.weight(1f))

                Icon(
                    painter = painterResource(id = R.drawable.check_circle_24px),
                    contentDescription = "Success Icon",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(80.dp)
                        .padding(bottom = 24.dp)
                )

                Text(
                    text = "Usuário criado com sucesso!",
                    fontSize = 20.sp,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "Seja bem vindo à uma comunidade que faz a diferença.",
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Medium,
                    color = GreyLighter,
                    modifier = Modifier.padding(bottom = 32.dp)
                )

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = onContinueClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryColor,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(6.dp),
                ) {
                    Text(
                        text = "VAMOS LÁ!",
                        fontSize = 16.sp,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewSuccessRegistrationScreen() {
    SuccessRegistrationScreen(
        onContinueClick = {},
        navController = NavHostController(LocalContext.current)
    )
}
