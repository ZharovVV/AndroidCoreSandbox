package com.github.zharovvv.android.core.sandbox.di

import com.github.zharovvv.android.core.sandbox.di.example.FeatureExample
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Scope

/**
 * Альтернативный способ создания зависимостей между компонентами через зависимости.
 * Подходит для многомодульной архитектуры.
 *
 * В данном случае можно сказать, что Subcomponent - это наследование (сабкомпонент - наследник родительского компонента),
 * а использование dependency - это композиция (Компонент _содержит_ другой компонент через зависимость.)
 * Чтобы использовать данный подход в многомодульном приложении - нужно включать не сам компонент
 * в зависимость, а через промежуточный интрефейс, который будет реализовывать компонент,
 * зависимости которого нам необходимы.
 */
@Scope
annotation class FeatureBScope

/**
 * В отличии от [FeatureComponent], который является сабкомпонентом и будет включен в состав класса
 * [DaggerAppComponent], [FeatureBComponent] - это компонент и он будет находиться в отдельном
 * сгенерированном классе [DaggerFeatureBComponent].
 */
@FeatureBScope
@Component(modules = [FeatureBModule::class], dependencies = [AppComponent::class])
interface FeatureBComponent {

    @Component.Builder
    interface Builder {

        fun withAppComponent(appComponent: AppComponent): Builder

        fun build(): FeatureBComponent
    }
}

@Module
class FeatureBModule {

    @FeatureBScope
    @Provides
    fun provideFeatureExample(): FeatureExample {
        return FeatureExample(data = "featureExampleData")
    }
}