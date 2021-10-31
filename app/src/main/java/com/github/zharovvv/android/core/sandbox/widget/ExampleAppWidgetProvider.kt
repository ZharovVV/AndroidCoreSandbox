package com.github.zharovvv.android.core.sandbox.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.util.Log

/**
 * [AppWidgetProvider] - класс-наследник [android.content.BroadcastReceiver].
 * Он просто получает от системы сообщение в onReceive, определяет по значениям из Intent,
 * какое именно событие произошло (добавление, удаление или обновление виджета),
 * и вызывает соответствующий метод (onEnabled, onUpdate и пр.).
 * В манифесте мы для нашего Receiver-класса настроили фильтр с action,
 * который ловит события update.
 *
 * Каким же образом этот Receiver ловит остальные события (например, удаление)?
 * Хелп пишет об этом так:
 * The <intent-filter> element must include an <action> element with the android:name attribute.
 * This attribute specifies that the AppWidgetProvider accepts the ACTION_APPWIDGET_UPDATE broadcast.
 * This is the only broadcast that you must explicitly declare.
 * The AppWidgetManager automatically sends all other App Widget broadcasts to the AppWidgetProvider as necessary.
 * Т.е. ACTION_APPWIDGET_UPDATE – это единственный action, который необходимо прописать явно.
 * Остальные события AppWidgetManager каким-то образом сам доставит до нашего AppWidgetProvider-наследника.
 */
class ExampleAppWidgetProvider : AppWidgetProvider() {

    companion object {
        private const val LOG_TAG = "WIDGET"
    }

    /**
     * Вызывается системой при создании первого экземпляра виджета
     * (мы ведь можем добавить в Home несколько экземпляров одного и того же виджета).
     */
    override fun onEnabled(context: Context?) {
        super.onEnabled(context)
        Log.i(LOG_TAG, "ExampleAppWidgetProvider#onEnabled")
    }

    /**
     * Вызывается при обновлении виджета.
     * На вход, кроме контекста, метод получает объект [appWidgetManager]
     * и список ID экземпляров виджетов [appWidgetIds], которые обновляются.
     * Именно этот метод обычно содержит код, который обновляет содержимое виджета.
     * Для этого нам нужен будет [AppWidgetManager], который мы получаем на вход.
     */
    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        Log.i(LOG_TAG, "ExampleAppWidgetProvider#onUpdate")
    }

    /**
     * Вызывается при удалении каждого экземпляра виджета.
     * На вход, кроме контекста, метод получает список ID экземпляров виджетов, которые удаляются.
     */
    override fun onDeleted(context: Context?, appWidgetIds: IntArray?) {
        super.onDeleted(context, appWidgetIds)
        Log.i(LOG_TAG, "ExampleAppWidgetProvider#onDeleted")
    }

    /**
     * Вызывается при удалении последнего экземпляра виджета.
     */
    override fun onDisabled(context: Context?) {
        super.onDisabled(context)
        Log.i(LOG_TAG, "ExampleAppWidgetProvider#onDisabled")
    }
}