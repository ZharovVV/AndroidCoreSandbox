package com.github.zharovvv.android.core.sandbox.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.TextView
import com.github.zharovvv.android.core.sandbox.service.BroadcastReceiverService.Companion.BR_SERVICE_EXTRA_KEY_NAME
import java.lang.ref.WeakReference

class ServiceResponseBroadcastReceiver(_textView: TextView) : BroadcastReceiver() {

    private val weakReference = WeakReference<TextView>(_textView)
    private val textView: TextView?
        get() {
            return weakReference.get()
        }

    companion object {
        const val BROADCAST_ACTION =
            "com.github.zharovvv.android.core.sandbox.service.ServiceResponseBroadcastReceiver"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.i("BroadcastReceiver", "$this#onReceive")
        textView?.apply {
            text = intent?.getStringExtra(BR_SERVICE_EXTRA_KEY_NAME)
        }
    }
}