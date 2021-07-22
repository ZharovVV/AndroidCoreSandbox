package com.github.zharovvv.android.core.sandbox

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.github.zharovvv.android.core.sandbox.StartForResultActivity.Companion.EXTRA_DATA_NAME_START_FOR_RESULT_ACTIVITY

class TrueMainActivity : AppCompatActivity() {

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

    private lateinit var textView: TextView
    private lateinit var button1: Button
    private lateinit var button2: Button
    private lateinit var button3: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_true_main)
        textView = findViewById(R.id.main_text_view)
        button1 = findViewById(R.id.button_1)
        button2 = findViewById(R.id.button_2)
        button1.setOnClickListener {
            startActivity<ExplicitCallExampleActivity> { intent ->
                intent.putExtra(EXTRA_DATA_NAME_FOR_SECOND_ACTIVITY, "Data from TrueMainActivity")
            }
        }
        button2.setOnClickListener {
            val intent = Intent("com.github.zharovvv.android.core.sandbox.intent.action.showdate")
            startActivity(intent)
        }
        button3 = findViewById(R.id.button_3)
        button3.setOnClickListener {
            val intent = Intent(this, StartForResultActivity::class.java)
            startActivityForResult(intent, START_ACTIVITY_FOR_RESULT_REQUEST_CODE)  //deprecated use
        }
    }

    /**
     * deprecated use
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