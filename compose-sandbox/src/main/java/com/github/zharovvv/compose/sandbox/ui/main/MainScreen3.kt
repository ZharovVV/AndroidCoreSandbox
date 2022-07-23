package com.github.zharovvv.compose.sandbox.ui.main

import androidx.compose.foundation.layout.*
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen3(navController: NavHostController? = null, viewModel: Compose3ViewModel) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Column {
                Text(text = "Work in progress...")
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