package com.github.zharovvv.android.core.sandbox.alarm.manager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import com.github.zharovvv.android.core.sandbox.LogLifecycleAppCompatActivity
import com.github.zharovvv.android.core.sandbox.databinding.ActivityAlarmManagerExampleBinding

class AlarmManagerExampleActivity : LogLifecycleAppCompatActivity() {

    private lateinit var alarmManager: AlarmManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAlarmManagerExampleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val sendBroadcastIntent = Intent(this, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(applicationContext, 0, sendBroadcastIntent, 0)
        with(binding) {
            startAlarmManagerButton.setOnClickListener {
                scheduleAlarm(
                    operation = pendingIntent,
                    alarmManager = alarmManager
                )
            }
            cancelAlarmManagerButton.setOnClickListener {
                alarmManager.cancel(pendingIntent)
            }
        }
    }

    /**
     * Два основных метода: [AlarmManager.set] и [AlarmManager.setRepeating].
     * Они позволяют запланировать, соответственно, разовое и повторяющееся событие.
     * UPD: Для большинства приложений [AlarmManager.setInexactRepeating] это правильный выбор.
     * Когда вы используете этот метод, Android синхронизирует несколько неточно
     * повторяющихся сигналов тревоги и запускает их одновременно.
     * Это снижает расход заряда батареи.
     * With setInexactRepeating(), you can't specify a custom interval the way you can with setRepeating().
     * You have to use one of the interval constants, such as INTERVAL_FIFTEEN_MINUTES, INTERVAL_DAY,
     * and so on. See AlarmManager for the complete list.
     *
     * На вход эти методы принимают тип будильника.
     * Тип __RTC__ - ориентируется на системное время.
     * Время запуска таких будильников надо указывать относительно [System.currentTimeMillis].
     *
     * Тип __ELAPSED_REALTIME__ - ориентируется на время запуска оси (ОС),
     * т.е. на время включения устройства. Время запуска таких будильников надо указывать относительно
     * [android.os.SystemClock.elapsedRealtime].
     *
     * Типы с постфиксом ___WAKEUP__ - при срабатывании будут выводить устройство из спячки.
     * Тут надо не путать спячку с выключенным экраном.
     * Экран может быть выключен, но устройство вовсю будет работать.
     * А когда задач нет устройство уходит в спячку, вырубая процессор для экономии энергии.
     * Если будильники с типом не WAKEUP должны сработать, а устройство в это время спит,
     * то их запуск откладывается до пробуждения. А WAKEUP-будильники разбудят устройство.
     *
     * Типы будильника бывают следующими:
     * * [AlarmManager.ELAPSED_REALTIME]
     * * [AlarmManager.ELAPSED_REALTIME_WAKEUP]
     * * [AlarmManager.RTC]
     * * [AlarmManager.RTC_WAKEUP]
     *
     * Таким способом можно запускать BroadcastReceiver и Service (через PendingIntent).
     * Для запуска activity есть свои нюансы (E/ActivityTaskManager: Abort background activity starts from 10358).
     *
     * В отличие от WorkManager-а, при переустановке приложения, запланированная работа не продолжилась
     * (скорее всего из-за удаления PendingIntent).
     */
    private fun scheduleAlarm(operation: PendingIntent, alarmManager: AlarmManager) {
//        alarmManager.set(AlarmManager.RTC, System.currentTimeMillis() + 4000L, operation)
        // Начиная с Android 4.4 (уровень API 19), все повторяющиеся сигналы тревоги неточны.
        // Для точного сигнала тревоги см. https://developer.android.com/training/scheduling/alarms
        // Например нужен вызов setAlarmClock(), для которого в Android 12 требуется специальное разрешение
        alarmManager.setInexactRepeating(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            SystemClock.elapsedRealtime() + 3000L,
            AlarmManager.INTERVAL_FIFTEEN_MINUTES, //Value will be forced up to 60000 as of Android 5.1; don't rely on this to be exact
            operation
        )
    }
}