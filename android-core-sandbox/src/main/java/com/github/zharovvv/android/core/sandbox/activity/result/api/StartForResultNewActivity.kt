package com.github.zharovvv.android.core.sandbox.activity.result.api

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.github.zharovvv.android.core.sandbox.R
import com.github.zharovvv.android.core.sandbox.activity.result.api.StartActivityForResultNewContract.Companion.START_ACTIVITY_FOR_RESULT_CONTRACT_OUTPUT_CODE
import com.github.zharovvv.core.ui.activity.LogLifecycleAppCompatActivity

class StartForResultNewActivity : LogLifecycleAppCompatActivity(R.layout.activity_start_for_result_new) {

    private lateinit var editText: EditText
    private lateinit var okButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        editText = findViewById(R.id.edit_text)
        okButton = findViewById(R.id.button_ok)
        okButton.setOnClickListener {
            val intent = Intent()
            intent.putExtra(START_ACTIVITY_FOR_RESULT_CONTRACT_OUTPUT_CODE, editText.text.toString())
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}