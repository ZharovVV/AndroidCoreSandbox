package com.github.zharovvv.android.core.sandbox.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.widget.RemoteViews
import androidx.core.content.edit
import com.github.zharovvv.android.core.sandbox.R

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

        /**
         * За отображение виджета отвечает один процесс (какой-нибудь Home),
         * а код из [ExampleAppWidgetProvider] будет выполняться в другом, своем собственном процессе.
         * Поэтому у нас нет прямого доступа к view-компонентам виджета.
         * И мы не можем вызывать метода типа setText и setBackgroundColor напрямую.
         * Поэтому используется класс [RemoteViews], он предназначен для межпроцессной работы с view.
         *
         * Помним, что ОС Android - это многопользовательская система Linux,
         * в которой каждое приложение это отдельный пользователь.
         * По умолчанию каждое приложение работает в собственном процессе Linux.
         * У каждого процесса есть собственная виртуальная машина,
         * поэтому код приложения работает изолированно от других приложений.
         */
        fun updateWidget(
            widgetId: Int,
            context: Context?,
            appWidgetManager: AppWidgetManager?,
            sp: SharedPreferences?
        ) {
            // Читаем параметры из Preferences
            val widgetText: String =
                sp?.getString(ConfigureWidgetActivity.WIDGET_TEXT + widgetId, null) ?: return
            val widgetColor: Int = sp.getInt(ConfigureWidgetActivity.WIDGET_COLOR + widgetId, 0)
            // Настраиваем внешний вид виджета
            val widgetView: RemoteViews = RemoteViews(context?.packageName, R.layout.widget)
            widgetView.setTextViewText(R.id.widget_text_view, widgetText)
            widgetColor.let { widgetView.setInt(R.id.widget_text_view, "setBackgroundColor", it) }
            val startConfigureActivityPendingIntent = PendingIntent.getActivity(
                context,
                widgetId,
                Intent(context, ConfigureWidgetActivity::class.java).apply {
                    action = AppWidgetManager.ACTION_APPWIDGET_CONFIGURE
                    putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId)
                },
                PendingIntent.FLAG_IMMUTABLE //Missing PendingIntent mutability flag
            )
            widgetView.setOnClickPendingIntent(
                R.id.widget_button,
                startConfigureActivityPendingIntent
            )
            // Обновляем виджет
            appWidgetManager?.updateAppWidget(widgetId, widgetView)
        }
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
     *
     * -----------------------------------
     *
     * onUpdate вызвался ПЕРЕД конфигурированием (когда настройки виджета еще не были сохранены),
     * но не вызвался ПОСЛЕ завершения работы конфигурационного экрана
     * (когда настройки сохранены и их можно использовать для обновления).
     * Т.е. настройки мы в Activity сохранили, но виджет их пока не прочел.
     * Теперь эти настройки применятся только при следующем обновлении,
     * т.е. через 40 минут, как мы указывали в файле метаданных.
     * Зачем делается onUpdate перед вызовом Activity – непонятно.
     * К тому же хелп английским по белому цинично и жирным шрифтом заявляет, что не может такого быть:
     * "… when you implement the Activity: … The onUpdate() method will not be called when the App Widget is created."
     * В общем, это известный баг, смиримся с ним.
     * К тому же у нас в методе updateWidget стоит защита от этого – мы проверяем,
     * что в Preferences записан WIDGET_TEXT. Если его нет, то мы ничего не делаем.
     */
    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        Log.i(
            LOG_TAG,
            "ExampleAppWidgetProvider#onUpdate; appWidgetIds: ${
                appWidgetIds?.joinToString(separator = ",") { it.toString() }
            }}"
        )
        val sp = context?.getSharedPreferences(ConfigureWidgetActivity.WIDGET_PREF, MODE_PRIVATE)
        for (id in appWidgetIds!!) {
            updateWidget(id, context, appWidgetManager, sp)
        }
    }

    /**
     * Вызывается при удалении каждого экземпляра виджета.
     * На вход, кроме контекста, метод получает список ID экземпляров виджетов, которые удаляются.
     */
    override fun onDeleted(context: Context?, appWidgetIds: IntArray?) {
        super.onDeleted(context, appWidgetIds)
        Log.i(LOG_TAG, "ExampleAppWidgetProvider#onDeleted; appWidgetIds: ${
            appWidgetIds?.joinToString(separator = ",") { it.toString() }
        }")
        val sp = context?.getSharedPreferences(ConfigureWidgetActivity.WIDGET_PREF, MODE_PRIVATE)
        sp?.edit {
            appWidgetIds?.forEach { widgetId: Int ->
                remove(ConfigureWidgetActivity.WIDGET_TEXT + widgetId)
                remove(ConfigureWidgetActivity.WIDGET_COLOR + widgetId)
            }
        }
    }

    /**
     * Вызывается при удалении последнего экземпляра виджета.
     */
    override fun onDisabled(context: Context?) {
        super.onDisabled(context)
        Log.i(LOG_TAG, "ExampleAppWidgetProvider#onDisabled")
    }
}