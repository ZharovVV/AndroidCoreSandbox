package com.github.zharovvv.android.core.sandbox.di

import android.content.Context
import com.github.zharovvv.android.core.sandbox.di.example.*
import com.github.zharovvv.android.core.sandbox.di.example.network.NetworkServiceExample
import com.github.zharovvv.android.core.sandbox.di.example.network.NetworkServiceExampleImpl
import com.github.zharovvv.android.core.sandbox.di.example.providers.AndroidMainThreadSchedulerProvider
import com.github.zharovvv.android.core.sandbox.di.example.providers.IoSchedulerProvider
import com.github.zharovvv.android.core.sandbox.di.example.providers.ResourceManager
import com.github.zharovvv.android.core.sandbox.di.example.providers.SchedulerProvider
import com.github.zharovvv.android.core.sandbox.di.example.repository.ExampleRepository
import com.github.zharovvv.android.core.sandbox.di.example.repository.ExampleRepositoryImpl
import dagger.*
import javax.inject.Qualifier
import javax.inject.Scope

/**
 * # IoC
 * IoC - Инверсия управления - важный принцип объектно-ориентированного программирования,
 * используемый для уменьшения зацепления (связанности) в компьютерных программах.
 * Также архитектурное решение интеграции, упрощающее расширение возможностей системы,
 * при котором поток управления программы контролируется фреймворком.
 * В обычной программе программист сам решает, в какой последовательности делать вызовы процедур.
 * Но если используется фреймворк, программист может разместить свой код в определенных
 * точках выполнения (используя callback или другие механизмы),
 * затем запустить «главную функцию» фреймворка, которая обеспечит все выполнение
 * и вызовет код программиста тогда, когда это будет необходимо.
 * Как следствие, происходит утеря контроля над выполнением кода — это и называется
 * инверсией управления (фреймворк управляет кодом программиста, а не программист управляет фреймворком).
 *
 * Методы реализации:
 * * Шаблон «Фабрика» (англ. Factory pattern)
 * * Локатор служб
 * * __Внедрение зависимости__ (англ. Dependency injection)
 *
 * ```
 * Через конструктор (англ. Constructor injection)
 * Через метод класса (англ. Setter injection)
 * Через интерфейс внедрения (англ. Interface injection)
 * ```
 * * Контекстный поиск (англ. contextualized lookup)
 *
 * # DI
 * DI - Dependency injection - процесс предоставления внешней зависимости программному компоненту.
 * Является специфичной формой «инверсии управления» (англ. Inversion of control, IoC),
 * когда она применяется к управлению зависимостями.
 * В полном соответствии с принципом единственной обязанности объект отдаёт заботу
 * о построении требуемых ему зависимостей внешнему,
 * специально предназначенному для этого общему механизму.
 *
 * #
 * # Dagger 2
 * Dagger 2 является fork-ом Dagger (от Square) и разрабатывался с целью избавиться от слабых сторон
 * оригинального решения, например от рефлексии, которая значительно замедляла работу Android- приложений,
 * а также добавить валидацию графа зависимостей во время компиляции.
 *
 * Dagger работает на основе __APT__ (Java Annotation Processing Tool). Данная технология позволят
 * анализировать код, помеченный специальными аннотациями, и генерировать на его основе новый код.
 * Не может модифицировать существующий код, а только создавать новый. При любом изменении
 * аннотируемого кода, необходимо выполнить повторную компиляцию.
 *
 * Ограничения использования Dagger-а:
 * * Чтобы выполнить доставку зависимости в поля, методы или конструкторы - они ( поля, методы...)
 * должны быть публичными (или как минимум internal в контексте kotlin-а).
 *
 * Как любой другой DI-framework Dagger позволяет уменьшать связность между компонентами и позволяет
 * эффективно масштабировать приложение. Можно описать создание объектов 1 раз и использовать их везде
 * в приложении, а также легко контролировать их время жизни.
 *
 * #
 * [Component] - граф зависимостей (applicationContext в аналогии со Spring).
 * Компонент может содержать различные [Module].
 */
@Component(
    modules = [AppModule::class, ResourceModule::class], //подключаем модуль AppModule, из которого данный граф
    //зависимостей будет тянуть нужные ему зависимости.
    dependencies = [AppDependencies::class]
)
/**
 * Scope - позволяет определять как долго будут существовать зависимости и привязывать их к
 * жизни компонентов. Любой компонент может (и даже должен) быть помечен каким-то скоупом.
 * Любая зависимость в рамках компонента также может быть помечена этим скоупом, что означает
 * что эта зависимость будет создаваться в рамках этого компонента один раз и навсегда.
 * Стандартный скоуп - это Singleton.
 */
//@Singleton
@AppScope
interface AppComponent {

    fun computer(): Computer    //1 способ
    //val computer: Computer

    /**
     * Для метода внедрения зависимостей важна лишь сигнатура - аргументом метода должен быть
     * класс (интерфейс), в который необходимо доставить зависимости.
     */
//    fun inject(daggerExampleActivity: DaggerExampleActivity)    //2 способ //закоментили так как добавили метод в FeatureComponent.

    fun featureComponent(): FeatureComponent.Builder

    @Component.Builder
    interface Builder {

        /**
         * Аннотация, говорит, что передаваемый в методе билдера параметр - это зависимость,
         * которая должна быть добавлена в граф зависимостей (т.е. в Component).
         * Если необходимо создать несколько методов билдера, принимающих одинаковый тип параметра,
         * то нужно использовать аннотации-квалификаторы (кастомные, либо стандартные).
         * ```
         * @BindsInstance
         * fun withContext(@AppContext context: Context): Builder
         * ```
         */
        @BindsInstance
        fun withContext(context: Context): Builder

        fun withAppDependencies(appDependencies: AppDependencies): Builder

        /**
         * Требования к методу интерфейса, помеченного аннотацией Builder:
         * * У метода  не должно быть никаких параметров
         * * Он должен возвращать тип компонента
         */
        fun build(): AppComponent
    }
}

/**
 * Module - Annotates a class that contributes to the object graph.
 * В модулях можно провайдить объекты.
 * includes - позволяет вложить в модуль другие модули.
 * Крайне желательно разбивать модули логически, чтобы они не были большими.
 */
@Module(
    includes = [
        SimpleDaggerExampleModule::class,
        NetworkModule::class,
        SchedulersModule::class,
        RepositoryModule::class
    ],
    subcomponents = [FeatureComponent::class]
)
object AppModule

@Module(includes = [NetworkBindModule::class])
class NetworkModule {

    /**
     * Рекомендация:
     * Использовать @Inject на конструкторах по максимуму, а провайдить объекты в модуле только
     * в случае необходимости.
     */
//    @Provides //< -- Удаляем за ненадобностью, так как помечаем конструктор @Inject
//    fun provideNetworkService(): NetworkServiceExample {
//        return NetworkServiceExampleImpl()
//    }

}

@Module
interface NetworkBindModule {

    @Binds
    fun bindNetworkService(networkServiceExampleImpl: NetworkServiceExampleImpl): NetworkServiceExample
}

@Module(includes = [SchedulersBindModule::class])
class SchedulersModule

/**
 * Данный пример искуственный, просто хотим показать работу аннотации @Named.
 */
@Module
interface SchedulersBindModule {

    /**
     * Binds Позволяет делать привязывание одного типа к другому.
     * В данном случае мы привязываем тип IoSchedulerProvider к SchedulerProvider.
     */
    @Binds
    @Io
//    @Named("io")
    fun bindIoSchedulerProvider(ioSchedulerProvider: IoSchedulerProvider): SchedulerProvider

    @Binds
    @MainThread
//    @Named("mainThread")
    fun bindAndroidMainThreadSchedulerProvider(
        androidMainThreadSchedulerProvider: AndroidMainThreadSchedulerProvider
    ): SchedulerProvider
}

@Module(includes = [RepositoryBindModule::class])
class RepositoryModule

@Module
interface RepositoryBindModule {

    @Binds
    fun bindExampleRepository(
        exampleRepositoryImpl: ExampleRepositoryImpl
    ): ExampleRepository
}

@Module
class SimpleDaggerExampleModule {

    @Provides
    fun provideProcessor(): Processor {
        return Processor(name = "AB2021")
    }

    @Provides
    fun provideMotherBoard(): MotherBoard {
        return MotherBoard(name = "X7 3000")
    }

    @Provides
    fun provideRam(): RAM {
        return RAM(size = "16 GB")
    }

    @Provides
    fun provideComputer(
        processor: Processor,
        motherBoard: MotherBoard,
        ram: RAM
    ): Computer {
        return Computer(
            processor = processor,
            motherBoard = motherBoard,
            ram = ram
        )
    }
}

@Module
class ResourceModule {

    @Provides
//    @Singleton
    @AppScope
    fun provideResourceManager(context: Context): ResourceManager {
        return ResourceManager(context)
    }
}

/**
 * Из данной зависимости компонент может забирать необходимые для своей работы артефакты.
 * Все доступные зависимости определяются по геттерам.
 */
interface AppDependencies {

    val dependencyExample: DependencyExample
}

/**
 * Кастомные аннотации-квалификаторы
 */
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Io

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class MainThread

/**
 * Кастомная scope-аннотация.
 * Важные моменты:
 * * один и тот же Scope __можно__ использовать для разных Component
 * * один и тот же Scope __нельзя__ использовать для Component и Subcomponent
 * # Разница между Lazy и Scope
 * Инжект при помощи Lazy - это отложенное получение зависимости из графа, которая затем сохраняется
 * локально. Как только будет уничтожено локальное место хранения у нас снова будет запрашиваться
 * зависимость из графа. Scope же сохраняет зависимость на уровне компонента. И пока жив компонент,
 * любой, ткто запросит зависимость, получит одну и ту же зависимость.
 *
 * Если мы не хотим все время держать в памяти зависимость (как будет происходить со Scope),
 * но при этом мы можем часто запрашивать эту зависимость, то в этом случае лучше использовать аннотацию
 * [Reusable]. В этом случае зависимость будет какое-то время жить в компоненте.
 */
@Scope
annotation class AppScope