package com.connectdeaf.ui.screens

import RegisterUiState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController

import com.connectdeaf.ui.components.DrawerMenu
import com.connectdeaf.ui.theme.GreyLighter
import com.connectdeaf.ui.theme.PrimaryColor
import com.connectdeaf.viewmodel.DrawerViewModel
import com.connectdeaf.viewmodel.RegisterViewModel
import com.connectdeaf.viewmodel.factory.RegisterViewModelFactory

import kotlinx.coroutines.launch


@Composable
fun AddressScreen(
    registerViewModel: RegisterViewModel = viewModel(),
    navController: NavController,
    drawerViewModel: DrawerViewModel = viewModel()
) {
    val uiState by registerViewModel.uiState.collectAsState()

    // Para lidar com eventos de navegação
    val context = LocalContext.current

    // Para lidar com escopos de corrotina
    val scope = rememberCoroutineScope()

    // Navegação baseada no estado de "usuário criado"
    LaunchedEffect(uiState.isUserCreated) {
        if (uiState.isUserCreated) {
            navController.navigate("successRegistrationScreen")
        }
    }

    DrawerMenu(
        navController = navController,
        scope = scope,
        drawerViewModel = drawerViewModel,
    ) {
        Scaffold(
            topBar = {
                // Componente de barra superior personalizada
                com.connectdeaf.ui.components.TopAppBar(
                    onOpenDrawerMenu = { scope.launch { drawerViewModel.openMenuDrawer() } },
                    onOpenDrawerNotifications = { scope.launch { drawerViewModel.openNotificationsDrawer() } },
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
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Seção de cabeçalho
                AddressHeaderSection()

                Spacer(modifier = Modifier.height(24.dp))

                // Campos de entrada para endereço
                AddressInputFields(
                    uiState = uiState,
                    onCepChange = registerViewModel::onCepChange,
                    onStateChange = registerViewModel::onStateChange,
                    onCityChange = registerViewModel::onCityChange,
                    onStreetChange = registerViewModel::onStreetChange,
                    onNeighborhoodChange = registerViewModel::onNeighborhoodChange,
                    onNumberChange = registerViewModel::onNumberChange, // Adicionado
                    onComplementChange = registerViewModel::onComplementChange // Adicionado
                )

                Spacer(modifier = Modifier.weight(1f))

                // Botão de continuar
                Button(
                    onClick = {
                        registerViewModel.registerUser(context)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    enabled = uiState.isFormValid, // Validação do formulário
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (uiState.isFormValid) PrimaryColor else GreyLighter,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = "CONTINUAR",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}



@Composable
fun AddressHeaderSection() {
    Text(
        text = "Endereço",
        fontSize = 20.sp,
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Medium,
        color = Color.Black,
    )


    Spacer(modifier = Modifier.height(8.dp))


    Text(
        text = "Nos conte onde podemos ajudar você!",
        fontSize = 16.sp,
        fontFamily = FontFamily.SansSerif,
        color = Color.Black,
    )
}


@Composable
fun AddressInputFields(
    uiState: RegisterUiState,
    onCepChange: (String) -> Unit,
    onStateChange: (String) -> Unit,
    onCityChange: (String) -> Unit,
    onStreetChange: (String) -> Unit,
    onNeighborhoodChange: (String) -> Unit,
    onNumberChange: (String) -> Unit, // Adicionado para o campo Número
    onComplementChange: (String) -> Unit // Adicionado para o campo Complemento
) {
    OutlinedTextField(
        value = uiState.cep,
        onValueChange = onCepChange,
        label = { Text("CEP") },
        shape = RoundedCornerShape(5.dp),
        textStyle = TextStyle(color = Color.Black),
        modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(16.dp))

    Row {
        OutlinedTextField(
            value = uiState.state,
            onValueChange = onStateChange,
            label = { Text("Estado") },
            shape = RoundedCornerShape(5.dp),
            textStyle = TextStyle(color = Color.Black),
            modifier = Modifier.width(116.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))

        OutlinedTextField(
            value = uiState.city,
            onValueChange = onCityChange,
            label = { Text("Cidade") },
            shape = RoundedCornerShape(5.dp),
            textStyle = TextStyle(color = Color.Black),
            modifier = Modifier.fillMaxWidth()
        )
    }

    Spacer(modifier = Modifier.height(16.dp))

    OutlinedTextField(
        value = uiState.street,
        onValueChange = onStreetChange,
        label = { Text("Rua") },
        shape = RoundedCornerShape(5.dp),
        textStyle = TextStyle(color = Color.Black),
        modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(16.dp))

    Row {
        OutlinedTextField(
            value = uiState.number,
            onValueChange = onNumberChange,
            label = { Text("Número") },
            shape = RoundedCornerShape(5.dp),
            textStyle = TextStyle(color = Color.Black),
            modifier = Modifier.width(116.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))

        OutlinedTextField(
            value = uiState.neighborhood,
            onValueChange = onNeighborhoodChange,
            label = { Text("Bairro") },
            shape = RoundedCornerShape(5.dp),
            textStyle = TextStyle(color = Color.Black),
            modifier = Modifier.fillMaxWidth()
        )


    }

    Spacer(modifier = Modifier.height(16.dp))

    OutlinedTextField(
        value = uiState.complement,
        onValueChange = onComplementChange,
        label = { Text("Complemento") },
        shape = RoundedCornerShape(5.dp),
        textStyle = TextStyle(color = Color.Black),
        modifier = Modifier.fillMaxWidth()
    )
}


@Preview
@Composable
fun PreviewAddressScreen() {
    AddressScreen(navController = NavHostController(LocalContext.current))
}
