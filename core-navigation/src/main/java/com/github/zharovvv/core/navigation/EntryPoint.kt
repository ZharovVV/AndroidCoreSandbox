package com.github.zharovvv.core.navigation

import androidx.annotation.DrawableRes

data class EntryPoint(
    val name: String,
    val description: String?,
    @DrawableRes
    val iconResId: Int? = null,
    val routerProvider: () -> Router
)
