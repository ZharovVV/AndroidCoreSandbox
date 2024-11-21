package com.github.zharovvv.compose.sandbox.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.github.zharovvv.compose.sandbox.ui.Compose3ViewModel
import com.github.zharovvv.compose.sandbox.ui.main.cell.Polygon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen3(navController: NavHostController? = null, viewModel: Compose3ViewModel) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Work in progress...")
                Polygon(vertexCount = 5)
                Polygon(vertexCount = 6)
                Polygon(vertexCount = 7)
                Button(
                    onClick = { navController?.navigate("details") },
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text("to DetailsScreen")
                }
            }

        }
    }
}