package com.example.proyectologin005d.ui.pages

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.proyectologin005d.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen(navController: NavController, total: Int) {
    var cardName by remember { mutableStateOf("") }
    var cardNumber by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }
    var isCvvFocused by remember { mutableStateOf(false) }

    var cardNameError by remember { mutableStateOf<String?>(null) }
    var cardNumberError by remember { mutableStateOf<String?>(null) }
    var expiryDateError by remember { mutableStateOf<String?>(null) }
    var cvvError by remember { mutableStateOf<String?>(null) }

    val isFormValid by derivedStateOf {
        cardName.isNotBlank() && cardNameError == null &&
        cardNumber.isNotBlank() && cardNumberError == null &&
        expiryDate.isNotBlank() && expiryDateError == null &&
        cvv.isNotBlank() && cvvError == null
    }

    fun validateCardName() {
        cardNameError = if (cardName.isBlank()) "El nombre no puede estar vacío" else null
    }

    fun validateCardNumber() {
        cardNumberError = when {
            cardNumber.isBlank() -> "El número de tarjeta no puede estar vacío"
            !cardNumber.all { it.isDigit() } -> "Debe contener solo números"
            cardNumber.length != 16 -> "Debe tener 16 dígitos"
            else -> null
        }
    }

    fun validateExpiryDate() {
        expiryDateError = if (!expiryDate.matches(Regex("^(0[1-9]|1[0-2])\\/([0-9]{2})$"))) {
            "Formato MM/AA inválido"
        } else {
            null
        }
    }

    fun validateCvv() {
        cvvError = when {
            cvv.isBlank() -> "El CVV no puede estar vacío"
            !cvv.all { it.isDigit() } -> "Debe ser numérico"
            cvv.length !in 3..4 -> "Debe tener 3 o 4 dígitos"
            else -> null
        }
    }

    val rotation by animateFloatAsState(targetValue = if (isCvvFocused) 180f else 0f, label = "")

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // ... (Credit Card Visual)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = cardName,
            onValueChange = { cardName = it; validateCardName() },
            label = { Text("Nombre en la tarjeta") },
            modifier = Modifier.fillMaxWidth(),
            isError = cardNameError != null,
            supportingText = { if (cardNameError != null) Text(cardNameError!!) }
        )

        OutlinedTextField(
            value = cardNumber,
            onValueChange = { cardNumber = it; validateCardNumber() },
            label = { Text("Número de tarjeta") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            isError = cardNumberError != null,
            supportingText = { if (cardNumberError != null) Text(cardNumberError!!) }
        )

        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = expiryDate,
                onValueChange = { expiryDate = it; validateExpiryDate() },
                label = { Text("Expira (MM/AA)") },
                modifier = Modifier.weight(1f),
                isError = expiryDateError != null,
                supportingText = { if (expiryDateError != null) Text(expiryDateError!!) }
            )
            Spacer(modifier = Modifier.width(16.dp))
            OutlinedTextField(
                value = cvv,
                onValueChange = { cvv = it; validateCvv() },
                label = { Text("CVV") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f).onFocusChanged { isCvvFocused = it.isFocused },
                isError = cvvError != null,
                supportingText = { if (cvvError != null) Text(cvvError!!) }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { /* Lógica de pago */ },
            modifier = Modifier.fillMaxWidth(),
            enabled = isFormValid,
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD1DC))
        ) {
            Text("Pagar", color = Color.Black)
        }
    }
}