package com.example.netdownload

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels

import com.example.netdownload.ui.screens.NetScreen
import com.example.netdownload.viewmodel.NetViewModel


class MainActivity : ComponentActivity() {

    private val netViewModel: NetViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            androidx.compose.material.MaterialTheme {
                androidx.compose.material.Surface {
                    NetScreen(
                        viewModel = netViewModel
                    )
                }
            }
        }
    }
}