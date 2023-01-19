package com.github.zharovvv.android.core.sandbox.preferences

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.github.zharovvv.android.core.sandbox.R
import com.github.zharovvv.core.ui.activity.LogLifecycleAppCompatActivity

class PreferencesExampleActivity : LogLifecycleAppCompatActivity(R.layout.activity_preferences_example) {

    companion object {
        const val SOME_PREFERENCES_NAME = "SOME_PREFERENCES_NAME"
        const val PREFERENCE_KEY = "PREFERENCE_KEY"
    }

    private lateinit var editText: EditText
    private lateinit var buttonSave: Button
    private lateinit var buttonLoad: Button

    private val preferences: SharedPreferences by lazy {
        //Константа MODE_PRIVATE используется для настройки доступа и означает,
        //что после сохранения, данные будут видны только этому приложению.
        getSharedPreferences(SOME_PREFERENCES_NAME, MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        editText = findViewById(R.id.activity_preferences_example_edit_text)
        buttonSave = findViewById(R.id.button_save)
        buttonSave.setOnClickListener {
            saveData(editText.text.toString())
        }
        buttonLoad = findViewById(R.id.button_load)
        buttonLoad.setOnClickListener {
            editText.setText(loadData())
        }
    }

    private fun saveData(data: String) {
        val editor: SharedPreferences.Editor = preferences.edit()
        editor.putString(PREFERENCE_KEY, data)
        //Consider using apply() instead; commit writes its data to persistent storage immediately,
        // whereas apply will handle it in the background
        editor.apply()
    }

    private fun loadData(): String {
        return preferences.getString(PREFERENCE_KEY, null) ?: "could not find data!"
    }

    override fun onDestroy() {
        super.onDestroy()
        saveData(editText.text.toString())
    }
}