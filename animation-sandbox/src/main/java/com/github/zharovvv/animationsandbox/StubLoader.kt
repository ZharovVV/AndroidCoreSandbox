package com.github.zharovvv.animationsandbox

import android.content.Context
import android.os.SystemClock
import androidx.loader.content.AsyncTaskLoader

class StubLoader(context: Context) : AsyncTaskLoader<Int>(context) {

    /**
     * Поля в лоадере не уничтожаются, поэтому есть возможность проверить,
     * завершился ли запрос.
     */
    private var result: Int? = null

    /**
     * Метод onStartLoading вызывается в случае, когда нужно загрузить данные и
     * вернуть их в Callback. На самом деле этот метод вызывается как результат
     * вызова метода startLoading. Обычно метод startLoading вызывается классом
     * LoaderManager, и нам нет нужды использовать его самостоятельно.
     *
     * Вызывается при вызове метода #onStart в Activity
     */
    override fun onStartLoading() {
        super.onStartLoading()
        if (result != null) {
            deliverResult(result)   //Метод deliverResult возвращает данные в LoaderCallbacks
        } else {
            forceLoad() //метод forceLoad инициирует вызов метода onForceLoad
        }
    }

    /**
     * По сути, этот метод служит только для удобства и
     * логического разделения между методами жизненного цикла и методами для
     * загрузки данных. В методе onForceLoad вы должны загрузить данные асинхронно
     * и вернуть результат с помощью метода deliverResult.
     */
    override fun onForceLoad() {
        super.onForceLoad()
    }

    /**
     * Вызывается при вызове метода #onStop в Activity
     */
    override fun onStopLoading() {
        super.onStopLoading()
    }

    /**
     * Переопределение абстрактного метода в классе AsyncTaskLoader (отсутствует в Loader<D>)
     */
    override fun loadInBackground(): Int? {
        SystemClock.sleep(2000)
        result = 5
        return result
    }
}