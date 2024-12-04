package com.github.zharovvv.auth.core.di.api.internal.navigation

import com.github.zharovvv.auth.core.R
import com.github.zharovvv.auth.core.di.api.AuthCoreApi
import com.github.zharovvv.auth.core.ui.AuthActivity
import com.github.zharovvv.common.di.multibinds.FeatureApiKey
import com.github.zharovvv.core.navigation.EntryPoint.ActivityEntryPoint
import com.github.zharovvv.core.navigation.OnlyForMainScreen
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@OptIn(OnlyForMainScreen::class)
@Module
class AuthCoreNavigationModule {

    @Singleton
    @Provides
    @[IntoMap FeatureApiKey(AuthCoreApi::class)]
    fun composeSandboxEntryPoint(): ActivityEntryPoint =
        ActivityEntryPoint(
            name = "Авторизация",
            description = "Пример с OAuth 2.0 + GitHub",
            iconResId = R.drawable.ic_oauth_2,
            launcher = { context ->
                context.startActivity(AuthActivity.newIntent(context))
            }
        )
}