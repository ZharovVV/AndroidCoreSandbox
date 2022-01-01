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
 * у него будут сабкомпоненты. (В данном примере указали Сабкомпонент в модуле [AppModule].)
 *
 * При ипользовании Subcomponent-ов родительский компонент (т.е. компонент, в любом модуле которого
 * объявлен subcomponent) должен знать об этом сабкомпоненте. В результате получается _жесткая связь_.
 * Свзязано это с тем, что Dagger будет генерировать компонент и при этом будет вкладывать в этот
 * компонент все классы-сабкомпоненты. __Такой подход абсолютно не будет работать для модуляризации.__
 *
 * За время жизни subcomponent-а разработчик отвечает самостоятельно.
 *
 * Альтернативным способом создания зависимостей между компонентами является __использование зависимостей__.
 * (См. [FeatureBComponent])
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
