package com.github.zharovvv.android.core.sandbox.noncore.di.api

import android.content.Context
import com.github.zharovvv.android.core.sandbox.noncore.di.navigation.AndroidCoreSandboxRouter
import com.github.zharovvv.android.core.sandbox.noncore.di.navigation.AndroidCoreSandboxRouterImpl
import com.github.zharovvv.android.core.sandbox.notification.NotificationUtil
import com.github.zharovvv.android.core.sandbox.sqlite.PersonDatabase
import com.github.zharovvv.android.core.sandbox.sqlite.PersonDatabaseProvider
import com.github.zharovvv.common.di.qualifier.ApplicationContext
import com.github.zharovvv.common.di.scope.PerFeature
import com.github.zharovvv.rxjavasandbox.di.navigation.RxJavaSandboxRouter
import dagger.Module
import dagger.Provides

@Module
class AndroidCoreSandboxModule {

    @PerFeature
    @Provides
    fun provideAndroidCoreSandboxRouter(): AndroidCoreSandboxRouter {
        return AndroidCoreSandboxRouterImpl()
    }

    @PerFeature
    @Provides
    fun provideNotificationUtil(@ApplicationContext applicationContext: Context): NotificationUtil {
        return NotificationUtil(applicationContext).apply { createNotificationChannel() }
    }

    @PerFeature
    @Provides
    fun providePersonDatabase(@ApplicationContext applicationContext: Context): PersonDatabase {
        return PersonDatabaseProvider.getPersonDatabase(applicationContext)
    }

    //Пример когда, нужна зависимость другого модуля
    @PerFeature
    @Provides
    fun provideAnyWithDependencyFromAnotherModule(rxJavaSandboxRouter: RxJavaSandboxRouter): Any {
        return Any()
    }
}
