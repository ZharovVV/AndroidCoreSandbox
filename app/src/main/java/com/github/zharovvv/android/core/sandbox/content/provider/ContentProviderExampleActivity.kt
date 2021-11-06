package com.github.zharovvv.android.core.sandbox.content.provider

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.CallLog
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.github.zharovvv.android.core.sandbox.LogLifecycleAppCompatActivity
import com.github.zharovvv.android.core.sandbox.R
import com.github.zharovvv.android.core.sandbox.sqlite.Person

class ContentProviderExampleActivity : LogLifecycleAppCompatActivity() {

    private lateinit var handler: Handler
    private lateinit var requestCallLogPermissionLauncher: ActivityResultLauncher<String>

    @Suppress("SameParameterValue")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_provider_example)
        val outputTextView = findViewById<TextView>(R.id.custom_content_provider_output_text_view)
        val otherOutputTextView =
            findViewById<TextView>(R.id.system_content_provider_output_text_view)
        handler = Handler(Looper.getMainLooper())
        requestCallLogPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, "Доступ пердоставлен", Toast.LENGTH_SHORT).show()
                requestDataFromSystemContentProvider(updatedTextView = otherOutputTextView)
            } else {
                Toast.makeText(this, "Доступ не пердоставлен!", Toast.LENGTH_SHORT).show()
                otherOutputTextView.text = "Доступ не предоставлен!"
            }
        }
        requestDataFromCustomContentProvider(updatedTextView = outputTextView)
        if (checkPermission(Manifest.permission.READ_CALL_LOG)) {
            requestDataFromSystemContentProvider(updatedTextView = otherOutputTextView)
        }
    }

    private fun requestDataFromCustomContentProvider(updatedTextView: TextView) {
        val uri = Uri.parse("content://${CustomContentProvider.AUTHORITY}/persons/1")
        Thread {
            try {
                val cursor = contentResolver.query(uri, null, null, null, null)!!
                cursor.use {
                    val person: Person?
                    if (it.moveToFirst()) {
                        val idIndex = it.getColumnIndex("id")
                        val nameIndex = it.getColumnIndex("name")
                        val ageIndex = it.getColumnIndex("age")
                        person = Person(
                            it.getString(nameIndex),
                            it.getInt(ageIndex)
                        )
                        person.id = it.getLong(idIndex)
                    } else {
                        person = null
                    }
                    handler.post {
                        val output = if (person != null) {
                            "id: ${person.id}; name: ${person.name}; age: ${person.age}"
                        } else {
                            "Не нашлось ни одного пользователя!"
                        }
                        updatedTextView.text = output
                    }
                }
            } catch (e: Exception) {
                handler.post { updatedTextView.text = e.message }
            }
        }.start()
    }

    @Suppress("SameParameterValue")
    private fun checkPermission(permission: String): Boolean {
        when {
            ContextCompat.checkSelfPermission(
                this,
                permission
            ) == PackageManager.PERMISSION_GRANTED -> {
                return true
            }

            shouldShowRequestPermissionRationale(permission) -> {
                Toast.makeText(
                    this,
                    "Доступ к звонкам нужен для работы этого приложения.",
                    Toast.LENGTH_SHORT
                ).show()
                requestCallLogPermissionLauncher.launch(permission)
            }

            else -> {
                requestCallLogPermissionLauncher.launch(permission)
            }
        }
        return false
    }

    private fun requestDataFromSystemContentProvider(updatedTextView: TextView) {
        Thread {
            val lastOutgoingCall = CallLog.Calls.getLastOutgoingCall(this)
            handler.post { updatedTextView.text = lastOutgoingCall }
        }.start()
    }
}