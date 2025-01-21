package com.connectdeaf.ui.screens

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.connectdeaf.ui.components.GenericInputField
import com.connectdeaf.ui.components.HeaderSectionRegister
import com.connectdeaf.ui.components.TopAppBar
import com.connectdeaf.ui.theme.AppStrings
import com.connectdeaf.ui.theme.PrimaryColor
import com.connectdeaf.viewmodel.RegisterServiceViewModel
import com.connectdeaf.viewmodel.uistate.RegisterServicesUiState

@Composable
fun RegisterServiceScreen(
    registerServiceViewModel: RegisterServiceViewModel = viewModel(),
    navController: NavController,
    onClick: () -> Unit
) {
    val uiState by registerServiceViewModel.uiState.collectAsState()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                navController = navController,
                showBackButton = true
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HeaderSectionRegister(isService = true)

            Spacer(modifier = Modifier.height(8.dp))

            RegisterInputFields(
                uiState = uiState,
                onNameServiceChange = registerServiceViewModel::onNameServiceChange,
                onDescriptionChange = registerServiceViewModel::onDescriptionChange,
                onCategoryChange = registerServiceViewModel::onCategoryChange,
                onPriceChange = registerServiceViewModel::onPriceChange
            )

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier.fillMaxWidth()
                    .size(311.dp, 116.dp)
                    .drawDashedBorder(PrimaryColor)
                    .clickable {
                        selectImage(context) { uri ->
                            registerServiceViewModel.onImageSelected(uri)
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                if (uiState.imageUri == null) {
                    ImagePlaceholder()
                } else {
                    Image(
                        painter = rememberAsyncImagePainter(uiState.imageUri),
                        contentDescription = "Imagem Selecionada",
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            OutlinedButton(
                onClick = onClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                enabled = uiState.isFormValid,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (uiState.isFormValid) PrimaryColor else Color(0xFF999999),
                    contentColor = if (uiState.isFormValid) Color.White else PrimaryColor
                ),
                shape = RoundedCornerShape(6.dp)
            ) {
                Text(
                    "Salvar",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun RegisterInputFields(
    uiState: RegisterServicesUiState,
    onNameServiceChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onCategoryChange: (String) -> Unit,
    onPriceChange: (String) -> Unit
) {
    GenericInputField(
        value = uiState.nameService,
        onValueChange = onNameServiceChange,
        label = AppStrings.NAME_SERVICE
    )
    GenericInputField(
        value = uiState.description,
        onValueChange = onDescriptionChange,
        label = AppStrings.DESCRIPTION_SERVICE
    )
    GenericInputField(
        value = uiState.categories,
        onValueChange = onCategoryChange,
        label = AppStrings.CATEGORY_SERVICE
    )
    GenericInputField(
        value = uiState.price.toString(),
        onValueChange = onPriceChange,
        label = AppStrings.PRICE_SERVICE
    )
}

@Composable
fun ImagePlaceholder() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Upload",
            tint = PrimaryColor,
            modifier = Modifier.size(40.dp)
        )
        Text(text = "PNG ou JPG (max. 3MB)", color = PrimaryColor, fontSize = 12.sp)
    }
}

fun Modifier.drawDashedBorder(color: Color): Modifier {
    return this.drawBehind {
        val dashWidth = 6.dp.toPx()
        val dashGap = 4.dp.toPx()
        val paint = android.graphics.Paint().apply {
            this.color = color.toArgb()
            style = android.graphics.Paint.Style.STROKE
            strokeWidth = 2.dp.toPx()
            pathEffect = android.graphics.DashPathEffect(floatArrayOf(dashWidth, dashGap), 0f)
        }

        val path = android.graphics.Path().apply {
            addRoundRect(
                0f, 0f, size.width, size.height,
                8.dp.toPx(), 8.dp.toPx(),
                android.graphics.Path.Direction.CW
            )
        }

        drawContext.canvas.nativeCanvas.drawPath(path, paint)
    }
}

fun selectImage(context: Context, onImageSelected: (Uri) -> Unit) {
    val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
    if (context is Activity) {
        context.startActivityForResult(intent, 100)
    }
}

@Preview
@Composable
fun RegisterServiceScreenPreview() {
    RegisterServiceScreen(onClick = {}, navController = NavController(LocalContext.current))
}
