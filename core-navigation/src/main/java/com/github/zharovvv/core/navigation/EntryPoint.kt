package com.github.zharovvv.core.navigation

import androidx.annotation.DrawableRes

sealed interface EntryPoint {

    val name: String
    val description: String?
    val iconResId: Int?

    @OnlyForMainScreen
    data class ActivityEntryPoint(
        override val name: String,
        override val description: String?,
        @DrawableRes
        override val iconResId: Int? = null,
        val activityLauncherProvider: () -> ActivityLauncher
    ) : EntryPoint

    data class FragmentEntryPoint(
        override val name: String,
        override val description: String?,
        @DrawableRes
        override val iconResId: Int? = null,
        val fragmentLauncherProvider: () -> FragmentLauncher
    ) : EntryPoint
}

