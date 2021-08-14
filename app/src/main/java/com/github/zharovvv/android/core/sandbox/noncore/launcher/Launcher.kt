package com.github.zharovvv.android.core.sandbox.noncore.launcher

import android.app.Activity
import android.content.Intent
import com.github.zharovvv.android.core.sandbox.startActivity

data class Launcher(val id: String, val title: String) {

    private var launchBlock: (() -> Unit)? = null


    constructor(id: String, title: String, launchBlock: () -> Unit) : this(id, title) {
        this.launchBlock = launchBlock
    }

    fun launch() {
        launchBlock?.invoke()
    }
}

inline fun <reified T : Activity> Activity.launcherFor(launcherTitle: String? = null): Launcher {
    val launchedActivityName = T::class.java.simpleName
    return Launcher(launchedActivityName, launcherTitle ?: launchedActivityName) {
        this.startActivity<T>()
    }
}

inline fun <reified T : Activity> Activity.launcherFor(
    launcherTitle: String? = null,
    crossinline intentEnrichment: (intent: Intent) -> Intent //crossinline - запрещает нелокальный возврат из функции
//Например если в теле inline-функции передано лямбда-выражение, которое будет использоваться в другом контексте,
//то это не скомпилируется. Для того чтобы код скомпилировался необходимо отметить данную
//лямбду модификатором crossinline.
): Launcher {
    val launchedActivityName = T::class.java.simpleName
    return Launcher(launchedActivityName, launcherTitle ?: launchedActivityName) {
        this.startActivity<T>(intentEnrichment)
    }
}
