package com.github.zharovvv.android.core.sandbox

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.github.zharovvv.android.core.sandbox.TrueMainActivity.Companion.EXTRA_DATA_NAME_FOR_SECOND_ACTIVITY

class ExplicitCallExampleActivity : AppCompatActivity() {

    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explicit_call_example)
        textView = findViewById(R.id.second_activity_text_view)
        val stringData = intent.getStringExtra(EXTRA_DATA_NAME_FOR_SECOND_ACTIVITY)
        textView.text = stringData
    }
}

/**
 * Вызов Activity с помощью такого Intent – это __явный вызов__.
 * Т.е. с помощью класса мы явно указываем какое Activity хотели бы увидеть.
 * Это обычно используется внутри одного приложения.
 *
 * Существует также __неявный вызов__ Activity.
 * Он отличается тем, что при создании Intent мы используем не класс,
 * а заполняем параметры action, data, category определенными значениями.
 * Комбинация этих значений определяют цель, которую мы хотим достичь.
 * Например: отправка письма, открытие гиперссылки, редактирование текста, просмотр картинки,
 * звонок по определенному номеру и т.д.
 * В свою очередь для Activity мы прописываем __Intent Filter__ - это набор тех же параметров:
 * action, data, category (но значения уже свои - зависят от того, что умеет делать Activity).
 * И если параметры нашего Intent совпадают с условиями этого фильтра, то Activity вызывается.
 * Но при этом поиск уже идет по всем Activity всех приложений в системе.
 * Если находится несколько, то система предоставляет вам выбор,
 * какой именно программой вы хотите воспользоваться.
 */
inline fun <reified T : Activity> Activity.startActivity(
        intentEnrichment: (intent: Intent) -> Unit
) {
    val intent = Intent(this, T::class.java)
    intentEnrichment(intent)
    this.startActivity(intent)
}

inline fun <reified T : Activity> Activity.startActivity() {
    val intent = Intent(this, T::class.java)
    this.startActivity(intent)
}