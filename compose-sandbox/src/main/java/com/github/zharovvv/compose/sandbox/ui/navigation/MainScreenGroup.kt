package com.github.zharovvv.compose.sandbox.ui.navigation

import androidx.annotation.StringRes
import com.github.zharovvv.compose.sandbox.R

sealed class MainScreenGroup(val route: String, @StringRes val resourceId: Int) {
    object Screen1 : MainScreenGroup("screen1", R.string.screen1_name)
    object Screen2 : MainScreenGroup("screen2", R.string.screen2_name)
    object Screen3 : MainScreenGroup("screen3", R.string.screen3_name)
}

val mainScreenGroupItems = listOf(MainScreenGroup.Screen1, MainScreenGroup.Screen2, MainScreenGroup.Screen3)
