package com.github.zharovvv.compose.sandbox.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.zharovvv.compose.sandbox.ui.details.DetailsScreen

@Composable
fun RootNavComponent() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "main", route = "root") {
        mainGraph(navController)
        sideGraph(navController)
    }
}

fun NavGraphBuilder.mainGraph(navController: NavHostController) {
    composable("main") { MainScreenNavComponent(navController) }
}

fun NavGraphBuilder.sideGraph(navController: NavHostController) {
    composable("details") { DetailsScreen(navController, viewModel()) }
}