package com.github.zharovvv.android.core.sandbox.bundle

import android.annotation.SuppressLint
import android.os.Bundle
import com.github.zharovvv.android.core.sandbox.LogLifecycleAppCompatActivity
import com.github.zharovvv.android.core.sandbox.databinding.ActivityBundleDescriptionBinding

class BundleDescriptionActivity : LogLifecycleAppCompatActivity() {

    companion object {
        private const val BUNDLE_STRING_KEY = "BUNDLE_STRING_KEY"
    }

    private lateinit var binding: ActivityBundleDescriptionBinding

    /**
     * При изменении конфигурации
     * (Поворот экрана, изменение языка системы, уничтожение активити системой для использования занятых ей ресурсов)
     * активити уничтожается и создается заново.
     * Вызываются коллбэки onPause(), onStop(), onSaveInstanceState(), onDestroy()
     * – onCreate(), onStart(), onRestoreInstanceState(), onResume().
     *
     * Система вызывает onSaveInstanceState() и onRestoreInstanceState() только в том случае,
     * когда необходимо сохранить состояние,
     * например при повороте экрана или при убийстве активити для освобождения памяти.
     * Данные коллбэки не вызываются, если пользователь выходит из активити нажав Back
     * или если активити убивается вызовом finish().
     *
     * # Bundle
     * [Bundle] – это класс, реализующий ассоциативный массив,
     * т.е. хранящий пары ключ-значение. Имеет get() и put() методы для примитивов,
     * строк и объектов, которые реализуют интерфейсы Parcelable и Serializable.
     * Bundle используется для передачи данных между базовыми компонентами.
     * Также рекомендуется использовать Bundle для передачи данных между процессами,
     * потому что Bundle оптимизирован под маршалинг/демаршалинг.
     *
     * ## Где хранится Bundle из onSaveInstanceState?
     * * Данные, сохраненные через onSaveInstanceState(), __переживают остановку процесса приложения__.
     * Это значит, что Bundle хранится не в памяти приложения.
     * * Также известно, что onSaveInstanceState() __не сохраняет данные на диске__.
     * Это можно понять потому, что состояния приложений не могут быть восстановлены
     * после перезагрузки девайса.
     * * Кроме того документация класса Parcel, который используется для сохранения Parcelable
     * объектов в Bundle, не рекомендует использовать Parcel для persistent storage.
     *
     * Где же сохраняется Bundle из onSaveInstanceState()?
     *
     * __Ответ: в системном классе ActivityManagerService__.
     * ##
     * Для каждой запущенной активити создается инстанс класса __ActivityRecord__.
     * Этот класс имеет поле __icicle__ типа Bundle.
     * Именно в это поле сохраняется состояние после вызова onSaveInstanceState().
     * ##
     * При остановке активити Bundle отправляется в системный процесс через Bindler IPC.
     * Далее на классе ActivityManagerService вызывается метод activityStopped(),
     * который принимает объект Bundle.
     * Этот метод находит ActivityRecord, соответствующий остановленной активити
     * и записывает полученный Bundle в поле icicle класса ActivityRecord.
     * Данная реализация не задокументирована и может быть изменена в будущих версиях Android.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBundleDescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val dataFromSavedInstanceState: String? = savedInstanceState?.getString(BUNDLE_STRING_KEY)
        val dataFromCustomNonConfigurationInstance: String? =
            lastCustomNonConfigurationInstance as String?
        val data: String = "dataFromSavedInstanceState: $dataFromSavedInstanceState\n" +
                "dataFromCustomNonConfigurationInstance: $dataFromCustomNonConfigurationInstance"
        binding.bundleOutputTextView.text = data
    }

    /**
     * Чтобы сохранить состояние активити, вы должны переопределить метод onSaveInstanceState()
     * и положить данные в Bundle.
     *
     * onSaveInstanceState() вызывается после onStop() на версии API ≥ 28.
     * На API < 28 этот коллбэк вызывается перед onStop() и нет гарантий до или после onPause().
     */
    @SuppressLint("MissingSuperCall")
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(BUNDLE_STRING_KEY, "Bundle from onSaveInstanceState")
        super.onSaveInstanceState(outState)
    }

    /**
     * При реинициализации активити,
     * Bundle с сохраненным состоянием передается в onCreate() и в onRestoreInstanceState().
     *
     * onRestoreInstanceState() вызывается после onStart().
     */
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
    }

    //onRetainNonConfigurationInstance

    /**
     * call in ComponentActivity#onRetainNonConfigurationInstance
     * by default return null.
     *
     * Результат сохраняется в ActivityClientRecord#lastNonConfigurationInstances
     * в методе ActivityThread#performDestroyActivity.
     */
    override fun onRetainCustomNonConfigurationInstance(): Any {
        //Deprecated
        //Use a androidx.lifecycle.ViewModel to store non config state.
        return "Data from onRetainCustomNonConfigurationInstance"
    }
}