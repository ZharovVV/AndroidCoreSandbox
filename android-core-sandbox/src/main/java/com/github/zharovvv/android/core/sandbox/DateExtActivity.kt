package com.github.zharovvv.android.core.sandbox

import android.os.Bundle
import android.widget.TextView
import com.github.zharovvv.core.ui.activity.LogLifecycleAppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class DateExtActivity : LogLifecycleAppCompatActivity() {

    private lateinit var dateTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_date_ext)
        dateTextView = findViewById(R.id.date_text_view)
        val simpleDateFormat = SimpleDateFormat("EEE, MMM dd, yyyy", Locale.US)
        val date = simpleDateFormat.format(Date())
        dateTextView.text = date
    }
}