package com.connectdeaf.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.connectdeaf.data.repository.AuthRepository
import com.connectdeaf.ui.components.GenericInputField
import com.connectdeaf.viewmodel.SignInViewModel
import com.connectdeaf.viewmodel.factory.SignInViewModelFactory

@Composable
fun SignInScreen(
    navController: NavController
) {
    val context = LocalContext.current
    val authRepository = AuthRepository(context)
    val viewModel: SignInViewModel = viewModel(
        factory = SignInViewModelFactory(authRepository)
    )

    val uiState by viewModel.uiState.collectAsState()
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Entrar", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(bottom = 16.dp))

            GenericInputField(
                value = uiState.email,
                onValueChange = { viewModel.onEmailChange(it) },
                label = "Email",
                keyboardType = KeyboardType.Email,
                isError = uiState.emailError != null,
                errorMessage = uiState.emailError ?: ""
            )

            GenericInputField(
                value = uiState.password,
                onValueChange = { viewModel.onPasswordChange(it) },
                label = "Senha",
                isPassword = true,
                isError = uiState.passwordError != null,
                errorMessage = uiState.passwordError ?: "",
                passwordVisible = uiState.isPasswordVisible,
                onPasswordVisibilityChange = { viewModel.togglePasswordVisibility() }
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = uiState.rememberMe,
                    onCheckedChange = { viewModel.onRememberMeChange(it) }
                )
                Text("Lembrar de mim", modifier = Modifier.padding(start = 8.dp))
            }

            OutlinedButton(
                onClick = {
                    viewModel.onSignIn (
                        { navController.navigate("home") },
                        context
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF3D66CC),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(6.dp)
            ) {
                if (uiState.isSubmitting) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        "CONTINUAR",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            if (uiState.errorMessage != null) {
                Text(
                    text = uiState.errorMessage.orEmpty(),
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            TextButton(
                onClick = { /* Implementar recuperação de senha */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color(0xFF3D66CC)
                )
            ) {
                Text("Esqueci minha senha")
            }
        }
    }
}
