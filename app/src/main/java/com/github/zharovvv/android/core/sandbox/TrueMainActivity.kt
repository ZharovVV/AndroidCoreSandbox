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
import com.github.zharovvv.android.core.sandbox.async.task.AsyncTaskExampleActivity
import com.github.zharovvv.android.core.sandbox.call.system.app.CallSystemAppExampleActivity
import com.github.zharovvv.android.core.sandbox.handler.HandlerExampleActivity
import com.github.zharovvv.android.core.sandbox.menu.MenuExampleActivity
import com.github.zharovvv.android.core.sandbox.preferences.PreferencesExampleActivity

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
    private val startActivityForResultNewContract: ActivityResultContract<String, String?> = StartActivityForResultNewContract()

    private lateinit var textView: TextView
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_true_main)
        textView = findViewById(R.id.main_text_view)
        recyclerView = findViewById(R.id.main_recycler_view)

        //Использование Activity Result API
        //Регистрируем контракт (LifecycleOwners must call register before they are STARTED)
        //Коллбек сработает при получении результата
        val activityLauncher: ActivityResultLauncher<String> =
                registerForActivityResult(startActivityForResultNewContract) { result: String? ->
                    textView.text = getString(R.string.return_from_activity_new, result)
                }


        val launchers = listOf(
                Launcher("ExplicitCallExampleActivity", getString(R.string.start_activity_button_1)),
                Launcher("ImplicitCallExample", getString(R.string.start_activity_button_2)),
                Launcher("StartForResultActivity", getString(R.string.start_activity_button_3)),
                Launcher("StartForResultActivityNewContract", getString(R.string.start_activity_button_4)),
                Launcher("CallSystemAppExampleActivity", getString(R.string.start_activity_button_5)),
                Launcher("MenuExampleActivity", getString(R.string.start_activity_button_6)),
                Launcher("PreferencesExampleActivity", getString(R.string.start_activity_button_7)),
                Launcher("HandlerExampleActivity", getString(R.string.start_activity_button_8)),
                Launcher("AsyncTaskExampleActivity", getString(R.string.start_activity_button_9))
        )
        val launchersListAdapter = LaunchersListAdapter { launcherItem: Launcher ->
            when (launcherItem.id) {
                "ExplicitCallExampleActivity" -> {
                    startActivity<ExplicitCallExampleActivity> { intent ->
                        intent.putExtra(EXTRA_DATA_NAME_FOR_SECOND_ACTIVITY, "Data from TrueMainActivity")
                    }
                }
                "ImplicitCallExample" -> {
                    val intent = Intent("com.github.zharovvv.android.core.sandbox.intent.action.showdate")
                    startActivity(intent)
                }
                "StartForResultActivity" -> {
                    val intent = Intent(this, StartForResultActivity::class.java)
                    startActivityForResult(intent, START_ACTIVITY_FOR_RESULT_REQUEST_CODE)  //deprecated use
                }
                "StartForResultActivityNewContract" -> {
                    //Использование Activity Result API
                    activityLauncher.launch("input for launching")  //Запуск контракта
                }
                "CallSystemAppExampleActivity" -> startActivity<CallSystemAppExampleActivity>()
                "MenuExampleActivity" -> startActivity<MenuExampleActivity>()
                "PreferencesExampleActivity" -> startActivity<PreferencesExampleActivity>()
                "HandlerExampleActivity" -> startActivity<HandlerExampleActivity>()
                "AsyncTaskExampleActivity" -> startActivity<AsyncTaskExampleActivity>()
            }
        }
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@TrueMainActivity)
            adapter = launchersListAdapter
        }
        launchersListAdapter.submitList(launchers)
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
                        val result = data!!.getStringExtra(EXTRA_DATA_NAME_START_FOR_RESULT_ACTIVITY)
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
}