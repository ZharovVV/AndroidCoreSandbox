package com.github.zharovvv.android.core.sandbox.call.system.app

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.github.zharovvv.android.core.sandbox.R

class CallSystemAppExampleActivity : AppCompatActivity(R.layout.activity_call_system_app_example) {

    private lateinit var button1: Button
    private lateinit var button2: Button
    private lateinit var button3: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        button1 = findViewById(R.id.sys_button_1)
        button1.setOnClickListener {
            startSysApp(
                    //стандартный системный action – ACTION_VIEW
                    Intent.ACTION_VIEW, //Это константа в классе Intent – означает, что мы хотим просмотреть что-либо
                    "http://developer.android.com/reference/android/net/Uri.html"
            )
        }
        button2 = findViewById(R.id.sys_button_2)
        button2.setOnClickListener {
            startSysApp(Intent.ACTION_VIEW, "geo:55.754283,37.62002")
        }
        button3 = findViewById(R.id.sys_button_3)
        button3.setOnClickListener {
            startSysApp(
                    Intent.ACTION_DIAL, //открывает звонилку и набирает номер, указанный в data, но не начинает звонок.
                    "tel:12345"
            )
        }
    }

    private fun startSysApp(action: String, uriString: String) {
        val intent = Intent(action, Uri.parse(uriString))
        startActivity(intent)
    }
}