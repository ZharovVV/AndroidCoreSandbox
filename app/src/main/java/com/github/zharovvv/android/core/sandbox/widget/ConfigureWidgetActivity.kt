package com.github.zharovvv.android.core.sandbox.widget

import android.appwidget.AppWidgetManager
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import com.github.zharovvv.android.core.sandbox.R

/**
 * Данное Activity будет запускаться системой при добавлении нового экземпляра виджета
 * и на вход получать ID этого экземпляра.
 */
class ConfigureWidgetActivity : AppCompatActivity() {

    companion object {
        private const val LOG_TAG = "WIDGET"
        const val WIDGET_PREF = "widget_pref"
        const val WIDGET_TEXT = "widget_text_"
        const val WIDGET_COLOR = "widget_color_"
    }

    private var widgetID = AppWidgetManager.INVALID_APPWIDGET_ID
    private lateinit var resultValue: Intent
    private lateinit var editText: EditText
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(LOG_TAG, "onCreate config")

        // извлекаем ID конфигурируемого виджета
        val intent = intent
        val extras = intent.extras
        if (extras != null) {
            widgetID = extras.getInt(
                AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID
            )
        }
        // и проверяем его корректность
        if (widgetID == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish()
        }

        // формируем intent ответа
        resultValue = Intent()
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetID)

        // отрицательный ответ
        // Хелп рекомендует при создании Activity сразу формировать отрицательный результат.
        // В этом случае, если пользователь нажмет Назад, система получит ответ,
        // что виджет создавать не надо.
        setResult(RESULT_CANCELED, resultValue)
        setContentView(R.layout.activity_configure_widget)
        sharedPreferences = getSharedPreferences(WIDGET_PREF, MODE_PRIVATE)
        editText = findViewById(R.id.etText)
        val text: String? = sharedPreferences.getString(WIDGET_TEXT + widgetID, null)
        text?.let { editText.setText(it) }
    }


    fun onClick(v: View?) {
        val selRBColor = (findViewById<View>(R.id.rgColor) as RadioGroup)
            .checkedRadioButtonId
        var color: Int = Color.RED
        when (selRBColor) {
            R.id.radioRed -> color = Color.parseColor("#66ff0000")
            R.id.radioGreen -> color = Color.parseColor("#6600ff00")
            R.id.radioBlue -> color = Color.parseColor("#660000ff")
        }

        // Записываем значения с экрана в Preferences
        sharedPreferences.edit()
            .apply {
                putString(WIDGET_TEXT + widgetID, editText.text.toString())
                putInt(WIDGET_COLOR + widgetID, color)
            }.apply()
        //фикс бага с вызовом AppWidgetProvider#onUpdate перед вызовом ConfigureWidgetActivity:
        val appWidgetManager = AppWidgetManager.getInstance(this)
        ExampleAppWidgetProvider.updateWidget(widgetID, this, appWidgetManager, sharedPreferences)
        // положительный ответ
        setResult(RESULT_OK, resultValue)
        Log.i(LOG_TAG, "finish config $widgetID")
        finish()
    }
}