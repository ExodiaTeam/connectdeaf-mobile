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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.connectdeaf.R
import com.connectdeaf.ui.components.DropdownMenuField
import com.connectdeaf.ui.components.GenericInputField
import com.connectdeaf.ui.components.HeaderSectionRegister
import com.connectdeaf.ui.components.ProfilePictureSection
import com.connectdeaf.ui.theme.AppStrings
import com.connectdeaf.utils.PhoneVisualTransformation
import com.connectdeaf.viewmodel.RegisterProfessionalViewModel
import com.connectdeaf.viewmodel.uistate.RegisterProfessionalUiState
import com.connectdeaf.ui.theme.PrimaryColor

@Composable
fun RegisterProfessionalScreen(
    registerProfessionalViewModel: RegisterProfessionalViewModel = viewModel(),
    onClick: () -> Unit
) {
    val uiState by registerProfessionalViewModel.uiState.collectAsState()
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
            HeaderSectionRegister(isProfessional = true)
            ProfilePictureSection(
                onClick = onClick,
                imageResourceId = R.drawable.ic_launcher_background
            )
            Spacer(modifier = Modifier.height(8.dp))

            ProfessionalInputFields(
                uiState = uiState,
                onNameChange = registerProfessionalViewModel::onNameChange,
                onEmailChange = registerProfessionalViewModel::onEmailChange,
                onPhoneChange = registerProfessionalViewModel::onPhoneChange,
                onPasswordChange = registerProfessionalViewModel::onPasswordChange,
                onAreaDeAtuacaoChange = registerProfessionalViewModel::onAreaDeAtuacaoChange,
                passwordVisible = passwordVisible,
                onPasswordVisibilityChange = { passwordVisible = it },
                onQualificationChange = registerProfessionalViewModel::onQualificationChange
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(
                onClick = onClick,
                modifier = Modifier.fillMaxWidth(),
                enabled = uiState.isFormValid,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (uiState.isFormValid) PrimaryColor else Color(0xFF999999),
                    contentColor = if (uiState.isFormValid) Color.White else PrimaryColor
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
fun ProfessionalInputFields(
    uiState: RegisterProfessionalUiState,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onAreaDeAtuacaoChange: (String) -> Unit,
    onQualificationChange: (String) -> Unit,
    passwordVisible: Boolean,
    onPasswordVisibilityChange: (Boolean) -> Unit
) {
    GenericInputField(
        value = uiState.name,
        onValueChange = onNameChange,
        label = AppStrings.NAME
    )

    GenericInputField(
        value = uiState.email,
        onValueChange = onEmailChange,
        label = AppStrings.EMAIL,
        keyboardType = KeyboardType.Email,
        isError = !uiState.isEmailValid,
        errorMessage = AppStrings.INVALID_EMAIL
    )

    GenericInputField(
        value = uiState.phone,
        onValueChange = { newPhone -> onPhoneChange(newPhone.filter { it.isDigit() }.take(11)) },
        label = AppStrings.PHONE,
        keyboardType = KeyboardType.Phone,
        visualTransformation = PhoneVisualTransformation()
    )

    GenericInputField(
        value = uiState.password,
        onValueChange = onPasswordChange,
        label = AppStrings.PASSWORD,
        isPassword = true,
        passwordVisible = passwordVisible,
        onPasswordVisibilityChange = onPasswordVisibilityChange
    )

    GenericInputField(
        value = uiState.areaDeAtuacao,
        onValueChange = onAreaDeAtuacaoChange,
        label = AppStrings.AREA_ATUACAO,
        keyboardType = KeyboardType.Text
    )

    DropdownMenuField(
        value = uiState.selectedQualifications,
        label = AppStrings.QUALIFICATION,
        onValueChange = onQualificationChange,
        options = uiState.qualifications,
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview
@Composable
fun RegisterProfessionalScreenPreview() {
    RegisterProfessionalScreen(onClick = {})
}