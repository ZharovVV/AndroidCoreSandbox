package com.github.zharovvv.android.core.sandbox.handler

import android.animation.ValueAnimator
import android.animation.ValueAnimator.INFINITE
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ProgressBar
import android.widget.TextView
import com.github.zharovvv.android.core.sandbox.LogLifecycleAppCompatActivity
import com.github.zharovvv.android.core.sandbox.R
import java.lang.ref.WeakReference
import java.util.concurrent.TimeUnit

class HandlerExampleActivity : LogLifecycleAppCompatActivity(R.layout.activity_handler_example) {

    private lateinit var textView: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var animView: View

    /**
     * Создание хендлера, в котором потенциально _возможны утечки_.
     * Потому что создается анонимный внутренний класс, который неявно хранит ссылку на внешний класс.
     * Если какая-то операция будет выполнятся слишком долго (и в конце должен быть вызван [Handler],
     * то Activity со всеми своими ресурсами будет висеть в памяти до тех пор,
     * пока задача не будет отработана.
     */
    private val potentiallyLeakingHandler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            //MainThread (UI Thread)
            super.handleMessage(msg)
            Log.i(
                "Handler_m", "handleMessage: message#what ${msg.what};"
                        + " currentThread: ${Thread.currentThread()}"
            )
        }
    }

    /**
     * В данном случчае класс не является внутренним (чтобы в Kotlin сделать класс внутренни нужно
     * добавить ключевое слово inner), а значит он не хранит неявную ссылку на внешний класс.
     * А чтобы view: T мог собрать GC используем WeakReference<T>. TODO Конкретизировать после разбора, что такое WeakReference.
     */
    class LeakFreeHandler<in T : View>(looper: Looper, view: T) : Handler(looper) {
        private val weakReferenceView: WeakReference<T> = WeakReference(view)
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            val view: T? = weakReferenceView.get()
            //do something with view
        }
    }

    private val foregroundThread = Thread {
        //Background thread

        potentiallyLeakingHandler.post {
            //MainThread (UI Thread)
            progressBar.visibility = VISIBLE
            Log.i("Handler_m", "start background task")
            textView.text = getString(R.string.activity_handler_example_text_start)
        }

        //Background thread
        try {
            TimeUnit.MILLISECONDS.sleep(10000L)
        } catch (e: InterruptedException) {
            Thread.currentThread().interrupt()
            return@Thread
        }

        potentiallyLeakingHandler.post {
            //MainThread (UI Thread)
            progressBar.visibility = GONE
            Log.i("Handler_m", "finish background task")
            textView.text = getString(R.string.activity_handler_example_text_finish)
        }
        potentiallyLeakingHandler.sendEmptyMessage(16132342)

        //this.runOnUiThread(Runnable {  }) //Под капотом вызывается handler в Activity.
    }

    private lateinit var myLooperThread: MyLooperThread

    /**
     * # Handler
     * [Handler] – это класс, который используется для работы с очередью сообщений,
     * связанной с потоком.
     * Хэндлер
     * * позволяет отправлять сообщения в другие потоки с задержкой
     * или без, а также
     * * обрабатывать полученные из других потоков сообщения.
     *
     * Хэндлер всегда связан с [Looper], который в свою очередь связан с каким-либо потоком.
     * При создании хэндлера в конструктор можно передать объект Looper.
     * Если используется дефолтный конструктор, то хэндлер создается на текущем потоке.
     * Если с потоком не связан лупер, то при создании хэндлера бросается RuntimeException.
     * Методы post*() используются для шедулинга объектов Runnable,
     * которые выполняются на связанном с хэндлером потоке.
     * Объекты Runnable добавляются в очередь сообщений.
     * Метод post(action: Runnable) добавляет объект action в конец очереди
     * без специального условия по времени, т.е. action будет запущен,
     * как только это будет возможно.
     * Методы postAtTime() и postDelayed() принимают параметром конфигурацию времени
     * (точное время и задержку относительно текущего времени соответственно).
     * Класс Handler имеет методы send*(), аналогичные методам post*(),
     * но для шедулинга объектов Message, которые позволяют передать произвольный объект.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        myLooperThread = MyLooperThread()
        myLooperThread.start()
        super.onCreate(savedInstanceState)
        textView = findViewById(R.id.activity_handler_example_text_view)
        progressBar = findViewById(R.id.activity_handler_example_progress_bar)
        animView = findViewById(R.id.activity_handler_example_view)
        if (savedInstanceState == null) {
            val rotateValueAnimator = ValueAnimator.ofFloat(0f, 360f).apply {
                addUpdateListener { valueAnimator ->
                    animView.rotation = valueAnimator.animatedValue as Float
                }
                repeatCount = INFINITE
                duration = 1000
            }
            animView.visibility = VISIBLE
            rotateValueAnimator.start()
            // Способ 1
            //В данном примере все сообщения, передаваемые через хендлер,
            // будут выполняться в MainThread-e.
            potentiallyLeakingHandler.postDelayed({
                animView.visibility = GONE
                rotateValueAnimator.end()
            }, 3000)
            foregroundThread.start()
        }
        //В примерах выше messages обрабатываются в MainThread-е.

        // Способ 2
        //Пример создания своего Looper-а, в потоке которого будут обрабатываться сообщения.
        //стартуем свой поток, в котором подготавливается Looper и связанный
        //с ним Handler.

        val myLooperHandler = myLooperThread.handler
        //UI Thread
        myLooperHandler?.sendEmptyMessage(100500)
        //Message будет обработан в MyLooperThread-e.
    }

    class MyLooperThread : Thread("MyLooperThread") {

        private var _handler: Handler? = null
        val handler: Handler? get() = _handler

        override fun run() {
            //MyLooperThread
            Looper.prepare()
            _handler = object : Handler(Looper.myLooper()!!) {
                override fun handleMessage(msg: Message) {
                    //MyLooperThread
                    Log.i(
                        "Handler_m", "handleMessage: message#what ${msg.what};" +
                                " currentThread: ${currentThread()}"
                    )
                }
            }
            Looper.loop()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        //Плохая реализация.
        foregroundThread.interrupt()

        //если token = null, этот метод будет удалить все неотправленные сообщения,
        //отправленные текущим обработчиком, из очереди сообщений
        potentiallyLeakingHandler.removeCallbacksAndMessages(null)
        myLooperThread.handler?.looper?.quit() // or quitSafety
    }
}