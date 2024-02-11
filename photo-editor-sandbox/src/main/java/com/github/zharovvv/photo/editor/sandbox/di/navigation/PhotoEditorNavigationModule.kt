package com.github.zharovvv.photo.editor.sandbox.di.navigation

import com.github.zharovvv.common.di.multibinds.FeatureApiKey
import com.github.zharovvv.core.navigation.EntryPoint.ActivityEntryPoint
import com.github.zharovvv.core.navigation.OnlyForMainScreen
import com.github.zharovvv.photo.editor.sandbox.di.api.PhotoEditorApi
import com.github.zharovvv.photo.editor.sandbox.presentation.PhotoEditorActivity
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@OptIn(OnlyForMainScreen::class)
@Module
class PhotoEditorNavigationModule {

    @Singleton
    @Provides
    @[IntoMap FeatureApiKey(PhotoEditorApi::class)]
    fun photoEditorEntryPoint(): ActivityEntryPoint =
        ActivityEntryPoint(
            name = "Photo Editor",
            description = "Песочница для Photo Editor",
            launcher = { context ->
                PhotoEditorActivity.newIntent(context).let(context::startActivity)
            }
        )
}