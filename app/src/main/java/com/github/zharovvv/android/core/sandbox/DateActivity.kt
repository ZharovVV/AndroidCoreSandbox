package com.github.zharovvv.android.core.sandbox

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class DateActivity : AppCompatActivity() {

    private lateinit var dateTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_date)
        dateTextView = findViewById(R.id.date_text_view)
        val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.US)
        val date = simpleDateFormat.format(Date())
        dateTextView.text = date
    }
}