package com.github.zharovvv.compose.sandbox.ui.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.zharovvv.compose.sandbox.ui.theme.AppTheme

@Preview
@Composable
fun MainScreen2Preview() {
    AppTheme(useDarkTheme = true, isDynamic = false) {
        MainScreen2(viewModel())
    }
}