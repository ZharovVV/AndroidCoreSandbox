package com.github.zharovvv.compose.sandbox.ui.navigation

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.github.zharovvv.compose.sandbox.ui.details.DetailsScreen
import com.github.zharovvv.compose.sandbox.ui.main.MainScreen
import com.github.zharovvv.compose.sandbox.ui.main.MainScreen2
import com.github.zharovvv.compose.sandbox.ui.main.MainScreen3

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenNavGraph(rootNavController: NavHostController? = null) {
    mapOf<String, String>().containsKey("")
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            val shouldShowBottomBar: Boolean = mainScreenTabs.any { mainScreenGroup ->
                mainScreenGroup.route == currentDestination?.route
            }
            if (shouldShowBottomBar) {
                NavigationBar {
                    mainScreenTabs.forEach { screen ->
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    imageVector = when (screen) {
                                        MainScreenGroup.Screen1 -> Icons.Filled.Home
                                        MainScreenGroup.Screen2 -> Icons.Filled.Favorite
                                        MainScreenGroup.Screen3 -> Icons.Filled.Settings
                                    },
                                    contentDescription = null
                                )
                            },
                            label = { Text(stringResource(screen.resourceId)) },
                            alwaysShowLabel = false,
                            selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                            onClick = {
                                if (screen.route != navController.currentDestination?.route) {
                                    navController.navigate(screen.route) {
                                        // Pop up to the start destination of the graph to
                                        // avoid building up a large stack of destinations
                                        // on the back stack as users select items
                                        //                                    navController.graph.findStartDestination().id
                                        popUpTo(MainScreenGroup.Screen1.route) {
                                            //TODO не совсем понятно что это делает
                                            //1) Но если saveState = false, то тогда происходит именно то поведение
                                            //которое нужно - по кнопке Назад или при выборе StartDestination
                                            //другое destination удаляется из бекстека, а у вьюмодели, объявленной
                                            //в рамках NavGraphBuilder.composable, будет вызван onCleared

                                            //2) Если же saveState = true, то тогда поведение, описанное выше,
                                            // будет только по кнопке Назад
                                            // при условии что NavOptionsBuilder.restoreState = true.
                                            // Если NavOptionsBuilder.restoreState = false, то с вью-моделью
                                            // происходит трешанина (создается куча экземпляров вью-модели).

                                            //позволяет сохранять состояние destination
                                            //работает в паре с NavOptionsBuilder.restoreState ???
                                            //                                    saveState = true
                                        }
                                        // Avoid multiple copies of the same destination when
                                        // reselecting the same item
                                        launchSingleTop = true
                                        // TODO разобраться для чего это (не совсем понятно)
                                        // Restore state when reselecting a previously selected item
                                        // работает в паре с PopUpToBuilder.saveState ???
                                        //                                restoreState = true
                                    }
                                }
                            }
                        )
                    }
                }
            }
        },
        modifier = Modifier.systemBarsPadding()
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = MainScreenGroup.Screen1.route,
            Modifier.padding(innerPadding),
            route = "main"
        ) {
            composable(MainScreenGroup.Screen1.route) {
                DisposableEffect(key1 = navController) {
                    Log.i("ComposeMainActivity-DEBUG", "Screen1; new composition")
                    Log.i("ComposeMainActivity-DEBUG", navController.detailedInfo())
                    onDispose { Log.i("ComposeMainActivity-DEBUG", "Screen1; dispose") }
                }
                MainScreen(viewModel())
            }
            composable(MainScreenGroup.Screen2.route) {
                DisposableEffect(key1 = navController) {
                    Log.i("ComposeMainActivity-DEBUG", "Screen2; new composition")
                    Log.i("ComposeMainActivity-DEBUG", navController.detailedInfo())
                    onDispose { Log.i("ComposeMainActivity-DEBUG", "Screen2; dispose") }
                }
                MainScreen2(viewModel())
            }
            composable(MainScreenGroup.Screen3.route) {
                LaunchedEffect(key1 = navController) {}
                DisposableEffect(key1 = navController) {
                    Log.i("ComposeMainActivity-DEBUG", "Screen3; new composition")
                    Log.i("ComposeMainActivity-DEBUG", navController.detailedInfo())
                    onDispose { Log.i("ComposeMainActivity-DEBUG", "Screen3; dispose") }
                }
                MainScreen3(navController, viewModel())
            }
            sideGraph(navController)
        }
    }
}

fun NavGraphBuilder.sideGraph(navController: NavHostController) {
    navigation(startDestination = "details", route = "side") {
        //граф "side", вложенный в граф "main"
        composable(route = "details") { DetailsScreen(navController, viewModel()) }
    }
}

fun NavHostController.detailedInfo(): String = StringBuilder()
//    .appendLine("Graph:")
//    .appendLine(graph.toString())
//    .let {
//        graph.fold(initial = it) { accumulator: StringBuilder, navDestination: NavDestination ->
//            accumulator.appendLine(navDestination.toString())
//        }
//    }
    .appendLine("current BackStack: ")
    .let {
        backQueue.fold(initial = it) { acc, navBackStackEntry ->
            acc.appendLine("id = ${navBackStackEntry.id}; destination = ${navBackStackEntry.destination}")
        }
    }
    .toString()
