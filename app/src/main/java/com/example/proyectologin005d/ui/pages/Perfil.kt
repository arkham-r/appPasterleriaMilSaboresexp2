package com.example.proyectologin005d.ui.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.proyectologin005d.R
import com.example.proyectologin005d.data.model.User
import com.example.proyectologin005d.data.repository.AuthRepository
import kotlinx.coroutines.launch

@Composable
fun PerfilScreen(
    navController: NavController
) {
    val cs = MaterialTheme.colorScheme
    val ty = MaterialTheme.typography
    val context = LocalContext.current
    val authRepository = remember { AuthRepository(context) }
    var user by remember { mutableStateOf<User?>(null) }

    LaunchedEffect(Unit) {
        val username = authRepository.getSessionUsername()
        if (username != null) {
            user = authRepository.getUserByUsername(username)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            // Imagen por defecto del perfil
            Image(
                painter = painterResource(R.drawable.ic_default_profile),
                contentDescription = "Foto de perfil",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(cs.secondary.copy(alpha = 0.3f))
            )

            Spacer(Modifier.height(16.dp))

            // Nombre de usuario
            Text(
                text = user?.nombre ?: "Usuario",
                style = ty.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = cs.primary
            )

            // Correo electrónico
            Text(
                text = user?.correo ?: "correo@ejemplo.com",
                style = ty.bodyMedium,
                color = cs.onBackground.copy(alpha = 0.7f)
            )

            Spacer(Modifier.height(32.dp))

            Divider(
                color = cs.secondary.copy(alpha = 0.4f),
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            Text(
                text = "Beneficios activos",
                style = ty.titleMedium,
                color = cs.primary
            )
            Spacer(Modifier.height(8.dp))
            Text(
                "• 10% descuento permanente (FELICES50)\n• Regalo de cumpleaños con correo Duoc",
                style = ty.bodySmall,
                color = cs.onBackground.copy(alpha = 0.7f)
            )
        }

        // Botón de cerrar sesión (abajo)
        val scope = rememberCoroutineScope()
        Button(
            onClick = {
                scope.launch {
                    authRepository.logout()
                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = cs.secondary,
                contentColor = cs.onSecondary
            ),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text("Cerrar sesión", style = ty.titleSmall)
        }
    }
}
