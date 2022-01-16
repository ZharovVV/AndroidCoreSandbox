package com.github.zharovvv.android.core.sandbox

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.zharovvv.android.core.sandbox.StartForResultActivity.Companion.EXTRA_DATA_NAME_START_FOR_RESULT_ACTIVITY
import com.github.zharovvv.android.core.sandbox.activity.result.api.StartActivityForResultNewContract
import com.github.zharovvv.android.core.sandbox.alarm.manager.AlarmManagerExampleActivity
import com.github.zharovvv.android.core.sandbox.async.task.AsyncTaskExampleActivity
import com.github.zharovvv.android.core.sandbox.bundle.BundleDescriptionActivity
import com.github.zharovvv.android.core.sandbox.call.system.app.CallSystemAppExampleActivity
import com.github.zharovvv.android.core.sandbox.content.provider.ContentProviderExampleActivity
import com.github.zharovvv.android.core.sandbox.custom.view.CustomViewExampleActivity
import com.github.zharovvv.android.core.sandbox.data.binding.DataBindingExampleActivity
import com.github.zharovvv.android.core.sandbox.di.example.DaggerExampleActivity
import com.github.zharovvv.android.core.sandbox.handler.HandlerExampleActivity
import com.github.zharovvv.android.core.sandbox.location.LocationExampleActivity
import com.github.zharovvv.android.core.sandbox.menu.MenuExampleActivity
import com.github.zharovvv.android.core.sandbox.navigation.NavigationExampleActivity
import com.github.zharovvv.android.core.sandbox.noncore.launcher.Launcher
import com.github.zharovvv.android.core.sandbox.noncore.launcher.LaunchersListAdapter
import com.github.zharovvv.android.core.sandbox.noncore.launcher.launcherFor
import com.github.zharovvv.android.core.sandbox.pending.intent.PendingIntentExampleActivity
import com.github.zharovvv.android.core.sandbox.preferences.PreferencesExampleActivity
import com.github.zharovvv.android.core.sandbox.service.ServiceExampleActivity
import com.github.zharovvv.android.core.sandbox.sqlite.SQLiteExampleActivity
import com.github.zharovvv.android.core.sandbox.viewmodel.livedata.ViewModelLiveDataExampleActivity
import com.github.zharovvv.android.core.sandbox.viewmodel.rxjava.ViewModelRxJavaExampleActivity
import com.github.zharovvv.android.core.sandbox.window.WindowExampleActivity
import com.github.zharovvv.android.core.sandbox.work.manager.WorkManagerExampleActivity
import com.github.zharovvv.rxjavasandbox.MainActivity

class TrueMainActivity : LogLifecycleAppCompatActivity() {

    /**
     * # Task
     * __Task__ – группа из нескольких Activity, с помощью которых пользователь выполняет
     * определенную операцию. Обычно стартовая позиция для создания Task – это экран Домой (Home).
     * Находясь в Home вы вызываете какое-либо приложение из списка приложений или через ярлык.
     * Создается Task. И Activity приложения (которое отмечено как MAIN в манифест-файле)
     * помещается в этот Task как корневое. Task выходит на передний фон.
     * Если же при вызове приложения, система обнаружила, что в фоне уже существует Task,
     * соответствующий этому приложению, то она выведет его на передний план и создавать ничего не будет.
     * Когда Activity_A вызывает Activity_B, то Activity_B помещается на верх (в топ) Task и получает фокус.
     * Activity_A остается в Task, но находится в состоянии Stopped
     * (его не видно и оно не в фокусе). Далее, если пользователь жмет Back находясь в Activity_B,
     * то Activity_B удаляется из Task и уничтожается.
     * А Activity_A оказывается теперь на верху Task и получает фокус.
     * В каком порядке открывались (добавлялись в Task) Activity,
     * в таком порядке они и содержатся в Task.
     * Они никак специально не сортируются и не упорядочиваются внутри.
     * Набор Activity в Task еще называют __back stack__.
     *
     * Допустим у нас есть Task с несколькими Activity. Он на переднем фоне, мы с ним работаем сейчас.
     *
     * * если мы нажмем кнопку Home, то ничего не будет удалено, все Activity сохранятся в этом Task-е,
     * а сам Task просто уйдет на задний фон и его всегда можно будет вызвать оттуда,
     * снова вызвав приложение, Activity которого является корневым для Task-а.
     * Либо можно удерживать кнопку Home и мы увидим как раз список Task-ов,
     * которые расположены на заднем фоне.
     * * если же в активном Task-е несколько раз нажимать кнопку Назад,
     * то в итоге в стэке не останется Activity,
     * пустой Task будет удален и пользователь увидит экран Home.
     * (Если точнее, то Activity будет уничтожено, однако при повторном нажатии Home мы увидим наше активити)
     */

    companion object {
        const val EXTRA_DATA_NAME_FOR_SECOND_ACTIVITY = "SECOND_ACTIVITY"
        const val START_ACTIVITY_FOR_RESULT_REQUEST_CODE = 15
    }

    /**
     * # Activity Result API
     *
     * Отметим несколько неочевидных моментов, которые необходимо учитывать:
     * * Регистрировать контракты можно в любой момент жизненного цикла активности или фрагмента,
     * но вот запустить его до перехода в состояние CREATED нельзя.
     * (Если быть точнее, то LifecycleOwners must call register before they are STARTED.)
     * Общепринятый подход — регистрация контрактов как полей класса.
     *
     * * Не рекомендуется вызывать registerForActivityResult() внутри операторов if и when.
     * Дело в том, что во время ожидания результата процесс приложения может быть уничтожен системой
     * (например, при открытии камеры, которая требовательна к оперативной памяти).
     * И если при восстановлении процесса мы не зарегистрируем контракт заново,
     * результат будет утерян.
     *
     * * Если запустить неявный интент, а операционная система не сможет найти подходящую Activity,
     * выбрасывается исключение ActivityNotFoundException: “No Activity found to handle Intent”.
     * Чтобы избежать такой ситуации, необходимо перед вызовом launch()
     * или в методе getSynchronousResult() выполнить проверку resolveActivity()
     * c помощью PackageManager.
     */
    private val startActivityForResultNewContract: ActivityResultContract<String, String?> =
        StartActivityForResultNewContract()

    private lateinit var textView: TextView
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_true_main)
        textView = findViewById(R.id.main_text_view)
        recyclerView = findViewById(R.id.main_recycler_view)

        val launchersListAdapter = LaunchersListAdapter()
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@TrueMainActivity)
            adapter = launchersListAdapter
        }
        launchersListAdapter.submitList(buildLaunchers())
    }

    /**
     * deprecated use
     * метод onActivityResult() нарушает принцип единственной ответственности (особенно) если необходимо
     * получать результат из нескольких активити, ведь он отвечает и за получение и обработку результата
     * Activity1 и за получение и обработку данных от Activity2.
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            START_ACTIVITY_FOR_RESULT_REQUEST_CODE -> {
                when (resultCode) {
                    RESULT_OK -> {
                        val result =
                            data!!.getStringExtra(EXTRA_DATA_NAME_START_FOR_RESULT_ACTIVITY)
                        textView.text = getString(R.string.return_from_activity, result)
                    }
                    //Back pressed
                    RESULT_CANCELED -> {
                        textView.text = getString(R.string.empty_return)
                    }
                }
            }
            else -> {
            }
        }
    }

    private fun buildLaunchers(): List<Launcher> {
        //Использование Activity Result API
        //Регистрируем контракт (LifecycleOwners must call register before they are STARTED)
        //Коллбек сработает при получении результата
        val activityLauncher: ActivityResultLauncher<String> =
            registerForActivityResult(startActivityForResultNewContract) { result: String? ->
                textView.text = getString(R.string.return_from_activity_new, result)
            }

        return listOf(
            launcherFor<ExplicitCallExampleActivity>(getString(R.string.start_activity_button_1)) { intent ->
                intent.putExtra(
                    EXTRA_DATA_NAME_FOR_SECOND_ACTIVITY,
                    "Data from TrueMainActivity"
                )
            },
            Launcher("ImplicitCallExample", getString(R.string.start_activity_button_2)) {
                val intent =
                    Intent("com.github.zharovvv.android.core.sandbox.intent.action.showdate")
                startActivity(intent)
            },
            Launcher("StartForResultActivity", getString(R.string.start_activity_button_3)) {
                val intent = Intent(this, StartForResultActivity::class.java)
                startActivityForResult(
                    intent,
                    START_ACTIVITY_FOR_RESULT_REQUEST_CODE
                )  //deprecated use
            },
            Launcher(
                "StartForResultActivityNewContract",
                getString(R.string.start_activity_button_4)
            ) {
                //Использование Activity Result API
                activityLauncher.launch("input for launching")  //Запуск контракта
            },
            launcherFor<CallSystemAppExampleActivity>(getString(R.string.start_activity_button_5)),
            launcherFor<MenuExampleActivity>(getString(R.string.start_activity_button_6)),
            launcherFor<PreferencesExampleActivity>(getString(R.string.start_activity_button_7)),
            launcherFor<HandlerExampleActivity>(getString(R.string.start_activity_button_8)),
            launcherFor<AsyncTaskExampleActivity>(getString(R.string.start_activity_button_9)),
            launcherFor<ServiceExampleActivity>(getString(R.string.start_activity_button_10)),
            launcherFor<SQLiteExampleActivity>(getString(R.string.start_activity_button_11)),
            launcherFor<ViewModelRxJavaExampleActivity>(getString(R.string.start_activity_button_12)),
            launcherFor<ViewModelLiveDataExampleActivity>(getString(R.string.start_activity_button_13)),
            launcherFor<WorkManagerExampleActivity>(getString(R.string.start_activity_button_14)),
            launcherFor<DataBindingExampleActivity>(getString(R.string.start_activity_button_15)),
            launcherFor<LocationExampleActivity>(getString(R.string.start_activity_button_16)),
            launcherFor<NavigationExampleActivity>(getString(R.string.start_activity_button_17)),
            launcherFor<CustomViewExampleActivity>(getString(R.string.start_activity_button_18)),
            launcherFor<PendingIntentExampleActivity>(getString(R.string.start_activity_button_19)),
            launcherFor<AlarmManagerExampleActivity>(launcherTitle = "Alarm Manager"),
            launcherFor<ContentProviderExampleActivity>(launcherTitle = "Content Provider"),
            launcherFor<MainActivity>(launcherTitle = "RxJava"),
            launcherFor<com.github.zharovvv.animationsandbox.MainActivity>(launcherTitle = "Android Animation"),
            launcherFor<DaggerExampleActivity>(launcherTitle = "Dagger 2"),
            launcherFor<WindowExampleActivity>(launcherTitle = "Window, Surface"),
            launcherFor<BundleDescriptionActivity>(launcherTitle = "Bundle")
        )
    }
}