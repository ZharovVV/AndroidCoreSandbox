package com.github.zharovvv.android.core.sandbox

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class StartForResultActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DATA_NAME_START_FOR_RESULT_ACTIVITY = "StartForResultActivity"
    }

    private lateinit var editText: EditText
    private lateinit var okButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_for_result)
        editText = findViewById(R.id.edit_text)
        okButton = findViewById(R.id.button_ok)
        okButton.setOnClickListener {
            val intent = Intent()
            intent.putExtra(EXTRA_DATA_NAME_START_FOR_RESULT_ACTIVITY, editText.text.toString())
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}