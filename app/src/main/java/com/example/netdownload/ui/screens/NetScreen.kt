package com.example.netdownload.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.netdownload.R
import com.example.netdownload.viewmodel.NetViewModel


/**
 * The main screen for displaying location updates and handling permissions.
 * @param viewModel The ViewModel that holds the state and logic for location updates.
 */
@Composable
fun NetScreen(viewModel: NetViewModel) {

    var showText by remember { mutableStateOf(false) }
    var showImage by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = {
                viewModel.downloadWebPage() // Llama a la función del ViewModel para descargar la página web
                showText = true
                showImage = false
            }) {
                Text(text = "Download web page")
            }
            Button(onClick = {
                showText = false
                showImage = true
            }) {
                Text(text = "Download image")
            }
        }
        if (showText) {
            Text(
                text = viewModel.webPageContent.value ?: "Texto aquí",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                color = Color.Black
            )
        }
        if (showImage) {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.imagen),
                contentDescription = "Descripción de la imagen",
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

}
