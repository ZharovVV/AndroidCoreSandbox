package com.github.zharovvv.auth.core.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.zharovvv.auth.core.ui.AuthViewModel
import com.github.zharovvv.auth.core.ui.github.profile.GitHubProfileScreen
import com.github.zharovvv.auth.core.ui.login.PreLoginScreen
import com.github.zharovvv.auth.core.ui.login.model.AuthNavEffect

@Composable
internal fun RootNavGraph(
    viewModel: AuthViewModel
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "prelogin", route = "root") {
        composable("prelogin") { PreLoginScreen(viewModel) }
        composable("main") { GitHubProfileScreen(viewModel) }
    }
    LaunchedEffect(Unit) {
        viewModel.navEffects.collect { effect ->
            when (effect) {
                AuthNavEffect.OpenGitHubProfileScreen -> {
                    navController.popBackStack()
                    navController.navigate("main") {
                        popUpTo("main") { inclusive = true }
                        launchSingleTop = true
                    }
                }

                AuthNavEffect.OpenPreLoginScreen -> {
                    navController.popBackStack()
                    navController.navigate("prelogin") {
                        popUpTo("prelogin") { inclusive = true }
                        launchSingleTop = true
                    }
                }
            }
        }
    }
}