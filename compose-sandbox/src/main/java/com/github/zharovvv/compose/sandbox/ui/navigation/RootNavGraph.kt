package com.github.zharovvv.compose.sandbox.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun RootNavGraph() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "main", route = "root") {
        mainGraph(navController)
//        sideGraph(navController)
    }
}

fun NavGraphBuilder.mainGraph(navController: NavHostController) {
    composable("main") { MainScreenNavGraph(navController) }
}

//Переделал: теперь граф с деталями - является вложенным в main Graph
// (что логично, потому что запускается из него)
// а проблемы сс отображением BottomBar решил просто проверкой, что currentDestination входит в список
//табов для отображения в BottomBar-е
//fun NavGraphBuilder.sideGraph(navController: NavHostController) {
//    composable("details") { DetailsScreen(navController, viewModel()) }
//}