package com.github.zharovvv.android.core.sandbox.di.example.analytics

import javax.inject.Inject

class Analytics
@Inject constructor(
    private val trackers: Set<@JvmSuppressWildcards AnalyticsTracker>
    //Помечаем аннотацией JvmSuppressWildcards, чтобы Dagger смог внедрить зависимости в Set.
    //По умолчанию интерфейс Set в Kotlin ковариантен по типовому параметру, а значит типовой
    //параметр должне быть только в исходящей позиции. @JvmSuppressWildcards устраняет данное
    //поведение и Set ведет себя как в джава. (Помним, что Dagger это прежде всего Java-фреймворк.)
) {

    fun sendEvent(event: AnalyticsTracker.Event) {
        trackers.forEach { tracker ->
            tracker.trackEvent(event)
        }
    }
}