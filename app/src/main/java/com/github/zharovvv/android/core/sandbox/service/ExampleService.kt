package com.github.zharovvv.android.core.sandbox.service

import android.app.Activity
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

/**
 * Службы (Сервисы) в Android работают как фоновые процессы и представлены классом android.app.Service.
 * Они не имеют пользовательского интерфейса и нужны в тех случаях,
 * когда не требуется вмешательства пользователя. Сервисы работают в фоновом режиме,
 * выполняя сетевые запросы к веб-серверу, обрабатывая информацию, запуская уведомления и т.д.
 * Служба может быть запущена и будет продолжать работать до тех пор,
 * пока кто-нибудь не остановит её или пока она не остановит себя сама.
 *
 * Сервисы предназначены для длительного существования, в отличие от активностей.
 * Они могут работать, постоянно перезапускаясь, выполняя постоянные задачи или выполняя задачи,
 * требующие много времени.
 * Клиентские приложения устанавливают подключение к службам и используют это подключение
 * для взаимодействия со службой.
 * С одной и той же службой могут связываться множество клиентских приложений.
 * Android даёт службам более высокий приоритет, чем бездействующим активностям,
 * поэтому вероятность того, что они будут завершены из-за нехватки ресурсов, заметно уменьшается.
 * По сути, если система должна преждевременно завершить работу запущенного сервиса,
 * он может быть настроен таким образом, чтобы запускаться повторно,
 * как только станет доступно достаточное количество ресурсов.
 * В крайних случаях прекращение работы сервиса (например, задержка при проигрывании музыки)
 * будет заметно влиять на впечатления пользователя от приложения,
 * и в подобных ситуациях приоритет сервиса может быть повышен до уровня активности,
 * работающей на переднем плане.
 */
class ExampleService : Service() {

    companion object {
        const val START_SERVICE_CONSTANT = "START_SERVICE_CONSTANT"
        const val SERVICE_INPUT = "SERVICE_INPUT"
    }

    override fun onCreate() {
        super.onCreate()
        Log.i("ServiceLifecycle", "$this#onCreate")
    }

    /**
     * [startId] - это счетчик вызовов startService пока сервис запущен.
     * Счетчик сбрасывается, когда сервис будет остановлен методами stopService, stopSelf и пр.
     * После этого вызовы снова идут с единицы.
     *
     * Возвращает одну из следующих констант:
     * * START_NOT_STICKY – сервис не будет перезапущен после того, как был убит системой
     * * START_STICKY – сервис будет перезапущен после того, как был убит системой
     * * START_REDELIVER_INTENT – сервис будет перезапущен после того, как был убит системой.
     * Кроме этого, сервис снова получит все вызовы startService,
     * которые не были завершены методом stopSelf(startId).
     * * START_STICKY_COMPATIBILITY - нет гарантий, что сервис будет вызван снова (версия для совместимости)
     *
     *
     */
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        //UI Thread (или главный поток процесса, в котором запущен сервис)
        Log.i("ServiceLifecycle", "$this#onStartCommand; startId: $startId")
        if (intent != null) {
            Log.i(
                    "ServiceLifecycle",
                    intent.getStringExtra(SERVICE_INPUT) ?: "Интент есть, но нет данных"
            )
        } else {
            Log.i("ServiceLifecycle", "intent is null")
        }
        Log.i("ServiceLifecycle", when (flags) {
            START_FLAG_REDELIVERY -> "flags: START_FLAG_REDELIVERY"
            START_FLAG_RETRY -> "flags: START_FLAG_RETRY"
            else -> "flags: $flags" //Почему-то во всех случаях приходит 0???
        })
        Thread {
            someTask()

//            stopSelf() //Останавливает работу сервиса. Сервис уничтожается при первом же вызове этого метода.

            stopSelf(startId)// Сервис уничтожается, когда этот метод вызовет последний поступивший (last startId) вызов.
            //То есть, гипотетически, может получиться ситуация, когда последний вызов уже завршился,
            //а первый ещё выполняется, при этом сервис уже будет уничтожен.
            //Стоит иметь это в виду.
        }.start()
        if (intent != null) {
            return when (intent.getStringExtra(START_SERVICE_CONSTANT)) {
                "START_NOT_STICKY" -> START_NOT_STICKY
                "START_STICKY" -> START_STICKY
                "START_REDELIVER_INTENT" -> START_REDELIVER_INTENT
                else -> START_STICKY_COMPATIBILITY
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.i("ServiceLifecycle", "$this#onBind")
        return null
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.i("ServiceLifecycle", "$this#onUnbind")
        return super.onUnbind(intent)
    }

    override fun onRebind(intent: Intent?) {
        Log.i("ServiceLifecycle", "$this#onRebind")
        super.onRebind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("ServiceLifecycle", "$this#onDestroy")
    }

    private fun someTask() {
        for (i in 1..20) {
            Thread.sleep(1000L)
            Log.i("ServiceLifecycle", "${Thread.currentThread().name} : $i")
        }
    }
}

inline fun <reified T : Service> Activity.startService() {
    val intent = Intent(this, T::class.java)
    this.startService(intent)
}

inline fun <reified T : Service> Activity.startService(
        intentEnrichment: (intent: Intent) -> Unit
) {
    val intent = Intent(this, T::class.java)
    intentEnrichment(intent)
    this.startService(intent)
}

inline fun <reified T : Service> Activity.stopService() {
    val intent = Intent(this, T::class.java)
    this.stopService(intent)
}

inline fun <reified T : Service> Activity.stopService(
        intentEnrichment: (intent: Intent) -> Unit
) {
    val intent = Intent(this, T::class.java)
    intentEnrichment(intent)
    this.stopService(intent)
}