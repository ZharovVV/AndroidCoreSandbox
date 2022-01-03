package com.github.zharovvv.android.core.sandbox.di.example.analytics

import android.util.Log
import javax.inject.Inject

interface AnalyticsTracker {

    companion object {
        private const val LOG_TAG = "Analytics"
    }

    fun trackEvent(event: Event) {
        Log.i(LOG_TAG, "$this; ${event.name}:${event.value}")
    }

    data class Event(val name: String, val value: String)
}

class FirebaseAnalyticsTracker
@Inject constructor() : AnalyticsTracker {
    override fun toString(): String = "FirebaseAnalyticsTracker"
}

class FacebookAnalyticsTracker
@Inject constructor() : AnalyticsTracker {
    override fun toString(): String = "FacebookAnalyticsTracker"
}

class AppMetricaTracker
@Inject constructor() : AnalyticsTracker {
    override fun toString(): String = "AppMetricaTracker"
}