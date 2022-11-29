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
        //TODO плохо, что довольно тяжелая работа по созданию кучи объектов лежит именно здесь.
        // Имеет смысл проводить инициализацию графа непосредственно в onCreate Activity
        // И заменить провайдер просто на сам объект.
        val activityLauncherProvider: () -> ActivityLauncher
    ) : EntryPoint

    data class FragmentEntryPoint(
        override val name: String,
        override val description: String?,
        @DrawableRes
        override val iconResId: Int? = null,
        //TODO плохо, что довольно тяжелая работа по созданию кучи объектов лежит именно здесь.
        // Имеет смысл проводить инициализацию графа непосредственно в onAttach Fragment
        // И заменить провайдер просто на сам объект.
        val fragmentLauncherProvider: () -> FragmentLauncher
    ) : EntryPoint
}

