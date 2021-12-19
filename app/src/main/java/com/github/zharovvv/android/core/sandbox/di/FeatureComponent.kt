package com.github.zharovvv.android.core.sandbox.di

import com.github.zharovvv.android.core.sandbox.di.example.DaggerExampleActivity
import com.github.zharovvv.android.core.sandbox.di.example.FeatureExample
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import javax.inject.Scope

/**
 * Пример Subcomponent
 * FragmentComponent -> ActivityComponent -> FeatureComponent -> AppComponent
 * Чтобы связать Subcomponent и Component необходимо в любом из модулей компонента, указать, какие
 * у него будут сабкомпоненты.
 */
@Scope
annotation class FeatureScope

@FeatureScope
@Subcomponent(modules = [FeatureModule::class])
interface FeatureComponent {

    fun inject(daggerExampleActivity: DaggerExampleActivity)

    @Subcomponent.Builder
    interface Builder {

        fun build(): FeatureComponent
    }
}

@Module
class FeatureModule {

    @FeatureScope
    @Provides
    fun provideFeatureExample(): FeatureExample {
        return FeatureExample(data = "featureExampleData")
    }
}
