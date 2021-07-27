package com.github.zharovvv.android.core.sandbox.async.task

import android.os.AsyncTask
import android.widget.TextView
import com.github.zharovvv.android.core.sandbox.R
import java.lang.ref.WeakReference

/**
 * # AsyncTask
 * * объект AsyncTask должен быть создан в UI-потоке
 * * метод execute должен быть вызван в UI-потоке
 * * не вызывайте напрямую методы onPreExecute, doInBackground, onPostExecute и onProgressUpdate
 * * AsyncTask может быть запущен (execute) только один раз, иначе будет exception
 *
 * При описании класса-наследника AsyncTask мы в угловых скобках указываем три типа данных:
 * 1) Тип входных данных. Это данные, которые пойдут на вход AsyncTask
 * 2) Тип промежуточных данных. Данные, которые используются для вывода промежуточных результатов
 * 3) Тип возвращаемых данных. То, что вернет AsyncTask после работы.
 *
 */
class CustomAsyncTask(textView: TextView) : AsyncTask<String, Int, String?>() {

    private var weakReferenceTextView: WeakReference<TextView>? = WeakReference(textView)
    var textView: TextView?
        get() {
            return weakReferenceTextView?.get()
        }
        set(value) {
            weakReferenceTextView = if (value != null) {
                WeakReference(value)
            } else {
                null
            }
        }

    override fun onPreExecute() {
        //UI Thread
        super.onPreExecute()
        textView?.apply {
            text = context.getString(R.string.start)
        }
    }

    override fun doInBackground(vararg params: String?): String? {
        //Background thread
        for (i in 1..10) {
            try {
                Thread.sleep(1000L)
            } catch (e: Exception) {
                return "cancelled"
            }
            publishProgress(i)
        }
        try {
            Thread.sleep(1000L)
        } catch (e: Exception) {
            return "cancelled"
        }
        return "The End"
    }

    override fun onPostExecute(result: String?) {
        //UI Thread
        super.onPostExecute(result)
        result?.let {
            textView?.apply {
                text = it
            }
        }
    }

    override fun onProgressUpdate(vararg values: Int?) {
        //UI Thread
        super.onProgressUpdate(*values)
        textView?.apply {
            text = values[0].toString()
        }
    }

    override fun onCancelled(result: String?) {
        //UI Thread
        super.onCancelled(result)
    }
}