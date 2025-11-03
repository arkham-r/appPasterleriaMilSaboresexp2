@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.example.proyectologin005d.ui.pages

import android.net.Uri
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.layout.ContentScale
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.proyectologin005d.data.local.CatalogoProductoJson
import com.example.proyectologin005d.data.local.JsonReader
import com.example.proyectologin005d.viewmodel.CartViewModel

private fun assetUrlFromJsonPath(path: String?): String? {
    if (path.isNullOrBlank()) return null
    if (path.startsWith("http://") || path.startsWith("https://")) return path
    val cleaned = path.removePrefix("../assets/").removePrefix("./assets/").removePrefix("assets/").removePrefix("/")
    return "file:///android_asset/img/${Uri.encode(cleaned)}"
}

@Composable
fun ProductosScreen(cartViewModel: CartViewModel = viewModel()) {
    val context = LocalContext.current
    var productos by remember { mutableStateOf<List<CatalogoProductoJson>>(emptyList()) }
    var expanded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        productos = JsonReader.cargarCatalogo(context)
        expanded = true
    }

    val cardPadding by animateDpAsState(
        targetValue = if (expanded) 12.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    Scaffold(
        topBar = { TopAppBar(title = { Text("Catálogo Mil Sabores") }) },
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            items(productos) { p ->
                Card(Modifier.fillMaxWidth().padding(bottom = cardPadding)) {
                    Column(Modifier.padding(12.dp)) {
                        AsyncImage(
                            model = assetUrlFromJsonPath(p.imagen),
                            contentDescription = p.nombre,
                            modifier = Modifier.fillMaxWidth().height(160.dp),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(p.nombre, style = MaterialTheme.typography.titleMedium)
                        Text("Precio: CLP ${p.precio}")
                        Text("Categoría: ${p.categoria ?: "-"}")
                        Button(
                            onClick = { cartViewModel.addToCart(p) },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD1DC))
                        ) {
                           Text("Agregar al carrito", color = Color.Black)
                        }
                    }
                }
            }
        }
    }
}
