package com.example.netdownload.ui.screens

import android.widget.ImageView
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.netdownload.R
import com.example.netdownload.viewmodel.NetViewModel
import androidx.compose.runtime.livedata.observeAsState as observeAsState


/**
 * The main screen for displaying location updates and handling permissions.
 * @param viewModel The ViewModel that holds the state and logic for location updates.
 */
@Composable
fun NetScreen(viewModel: NetViewModel) {
    var showText by remember { mutableStateOf(false) }
    var showImage by remember { mutableStateOf(false) }

    val webPageContent by viewModel.webPageContent.observeAsState()
    val imageUrl by viewModel.imageUrl.observeAsState()

    LaunchedEffect(webPageContent) {
        // Observa los cambios en webPageContent y actualiza showText y showImage
        webPageContent?.let {
            showText = true
            showImage = false
        }
    }

    LaunchedEffect(imageUrl) {
        // Observa los cambios en imageUrl y actualiza showImage
        imageUrl?.let {
            showText = false
            showImage = true
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = {
                viewModel.downloadWebPage()
            }) {
                Text(text = "Download web page")
            }
            Button(onClick = {
                showText = false
                showImage = true
                viewModel.setImageUrl("https://s.inyourpocket.com/gallery/113383.jpg")
            }) {
                Text(text = "Download image")
            }
        }

        if (showText) {
            Text(
                text = webPageContent ?: "Texto aquí",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                color = Color.Black
            )
        }
        if (showImage) {
            val context = LocalContext.current
            val imageView = remember { ImageView(context) }
            viewModel.downloadAndSetImage(imageUrl ?: "", imageView)
            AndroidView(
                factory = { imageView }
            )
        }
    }
}
