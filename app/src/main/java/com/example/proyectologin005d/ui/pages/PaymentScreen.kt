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

    val rotation by animateFloatAsState(targetValue = if (isCvvFocused) 180f else 0f)
    val buttonScale by animateFloatAsState(
        targetValue = 1.05f,
        animationSpec = repeatable(
            iterations = Int.MAX_VALUE,
            animation = tween(durationMillis = 500, delayMillis = 200)
        )
    )

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .graphicsLayer {
                    rotationY = rotation
                    cameraDistance = 8 * density
                },
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xFF6F1E51), Color(0xFFED4C67), Color(0xFFF79F1F)),
                        )
                    )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    if (rotation < 90f) {
                        Image(painter = painterResource(id = R.drawable.logo), contentDescription = null, modifier = Modifier.height(40.dp))
                        Text(text = cardNumber.ifEmpty { "#### #### #### ####" }, color = Color.White)
                        Row {
                            Text(text = cardName.ifEmpty { "NOMBRE APELLIDO" }, color = Color.White)
                            Spacer(modifier = Modifier.weight(1f))
                            Text(text = expiryDate.ifEmpty { "MM/AA" }, color = Color.White)
                        }
                    } else {
                        Spacer(modifier = Modifier.height(40.dp))
                        Text(text = "CVV: ${cvv.ifEmpty { "###" }}", modifier = Modifier.align(Alignment.End), color = Color.White)
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = cardName,
            onValueChange = { cardName = it },
            label = { Text("Nombre en la tarjeta") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = cardNumber,
            onValueChange = { cardNumber = it },
            label = { Text("Número de tarjeta") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = expiryDate,
                onValueChange = { expiryDate = it },
                label = { Text("Expira") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            OutlinedTextField(
                value = cvv,
                onValueChange = { cvv = it },
                label = { Text("CVV") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .weight(1f)
                    .onFocusChanged { isCvvFocused = it.isFocused }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { /* Lógica de pago */ },
            modifier = Modifier.fillMaxWidth().scale(buttonScale),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD1DC))
        ) {
            Text("Pagar", color = Color.Black)
        }
    }
}