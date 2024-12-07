package com.connectdeaf.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.connectdeaf.R
import com.connectdeaf.utils.PhoneVisualTransformation
import com.connectdeaf.viewmodel.RegisterUiState
import com.connectdeaf.viewmodel.RegisterViewModel

@Composable
fun RegisterScreen(
    registerViewModel: RegisterViewModel = viewModel(),
    onClick: () -> Unit
) {
    val uiState by registerViewModel.uiState.collectAsState()
    var passwordVisible by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            com.connectdeaf.ui.components.TopAppBar(
                navController = null,
                showBackButton = true
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HeaderSection()
            com.connectdeaf.ui.components.ProfilePictureSection(
                onClick = onClick,
                imageResourceId = R.drawable.ic_launcher_background
            )
            ClientInputFields(
                uiState = uiState,
                onNameChange = registerViewModel::onNameChange,
                onEmailChange = registerViewModel::onEmailChange,
                onPhoneChange = registerViewModel::onPhoneChange,
                onPasswordChange = registerViewModel::onPasswordChange,
                passwordVisible = passwordVisible,
                onPasswordVisibilityChange = { passwordVisible = it }
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(
                onClick = onClick,
                modifier = Modifier.fillMaxWidth(),
                enabled = uiState.isInputValid,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (uiState.isInputValid) Color(0xFF478FCC) else Color(0xFF999999),
                    contentColor = if (uiState.isInputValid) Color.White else Color(0xFF478FCC)
                ),
                shape = RoundedCornerShape(6.dp)
            ) {
                Text(
                    "CONTINUAR",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            TextButton(
                onClick = onClick,
                modifier = Modifier.wrapContentHeight(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent, contentColor = Color(0xFF478FCC)
                ),
                shape = RoundedCornerShape(6.dp)
            ) {
                Text(
                    "Já tenho uma conta",
                    style = TextStyle(
                        textDecoration = TextDecoration.Underline,
                        color = Color(0xFF478FCC),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}

@Composable
fun HeaderSection() {
    Text(
        text = "Cadastro",
        fontSize = 20.sp,
        fontFamily = FontFamily.SansSerif,
        color = Color.Black,
        modifier = Modifier.padding(6.dp)
    )
    Text(
        text = "Me fala mais sobre você, cliente!",
        fontSize = 16.sp,
        fontFamily = FontFamily.SansSerif,
        color = Color.Black,
        modifier = Modifier.padding(8.dp)
    )
}

@Composable
fun ClientInputFields(
    uiState: RegisterUiState,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    passwordVisible: Boolean,
    onPasswordVisibilityChange: (Boolean) -> Unit
) {
    OutlinedTextField(
        value = uiState.name,
        onValueChange = onNameChange, // Chama a função para atualizar o nome
        label = { Text("Nome Completo") },
        shape = RoundedCornerShape(15.dp),
        textStyle = TextStyle(color = Color.Black),
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(14.dp))

    OutlinedTextField(
        value = uiState.email,
        onValueChange = { newEmail ->
            onEmailChange(newEmail) // Chama função de atualização
        },
        label = { Text("Email") },
        shape = RoundedCornerShape(15.dp),
        textStyle = TextStyle(color = Color.Black),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        modifier = Modifier.fillMaxWidth(),
        isError = !uiState.isEmailValid,  // Indica erro se o email for inválido
        supportingText = {
            if (!uiState.isEmailValid) {
                Text("Email inválido", color = MaterialTheme.colorScheme.error)
            }
        }
    )

    OutlinedTextField(
        value = uiState.phone, // Usando o estado do ViewModel
        onValueChange = { newPhone ->
            val filteredPhone = newPhone.filter { it.isDigit() }.take(11)
            onPhoneChange(filteredPhone)
        },
        label = { Text("Telefone") },
        shape = RoundedCornerShape(15.dp),
        textStyle = TextStyle(color = Color.Black),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        modifier = Modifier.fillMaxWidth(),
        visualTransformation = PhoneVisualTransformation()
    )
    Spacer(modifier = Modifier.height(14.dp))

    OutlinedTextField(
        value = uiState.password,
        shape = RoundedCornerShape(15.dp),
        textStyle = TextStyle(color = Color.Black),
        onValueChange = onPasswordChange,
        label = { Text("Senha") },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        modifier = Modifier.fillMaxWidth(),
        trailingIcon = {
            IconButton(onClick = { onPasswordVisibilityChange(!passwordVisible) }) {
                val icon =
                    if (passwordVisible) R.drawable.baseline_visibility_24 else R.drawable.baseline_visibility_off_24
                val description = if (passwordVisible) "Ocultar senha" else "Mostrar senha"
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = description
                )
            }
        }
    )
}

@Preview
@Composable
fun RegisterScreenPreview() {
    RegisterScreen(onClick = {})
}