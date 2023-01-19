package com.github.zharovvv.android.core.sandbox

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.github.zharovvv.core.ui.activity.LogLifecycleAppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class DateActivity : LogLifecycleAppCompatActivity() {

    private lateinit var dateTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_date)
        dateTextView = findViewById(R.id.date_text_view)
        val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.US)
        val date = simpleDateFormat.format(Date())
        dateTextView.text = date

        val action = intent.action  //Получаем action из интента, который вызвал это Activity
        Log.i("Actions", action ?: "action is empty")
    }
}