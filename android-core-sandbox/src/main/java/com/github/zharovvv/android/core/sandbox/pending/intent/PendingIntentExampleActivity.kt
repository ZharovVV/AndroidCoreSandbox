package com.github.zharovvv.android.core.sandbox.pending.intent

import android.app.Activity
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.github.zharovvv.android.core.sandbox.R
import com.github.zharovvv.core.ui.activity.LogLifecycleAppCompatActivity

class PendingIntentExampleActivity : LogLifecycleAppCompatActivity() {

    /**
     * # [Intent]
     *
     * Два основных атрибута интента: action и data.
     * * action – строковая переменная, задающая действие, которое будет выполнено.
     * * data – объект класса Uri, описывающий данные, над которыми будет выполнено действие.
     * Например Intent с action == ACTION_CALL и data == Uri.parse("tel:$number")
     * выполняет звонок на заданный номер телефона.
     *
     *
     * Дополнительные атрибуты интента:
     * * category дает дополнительную информацию о действии, которое будет выполнено.
     * * type явно задает MIME type данных, которые передаются в data.
     * * component задает имя класса компонента (например Activity), который должен быть запущен.
     * Если задать атрибут component, то все остальные атрибуты становятся опциональными.
     * * extras – объект Bundle, который содержит дополнительные данные,
     * передаваемые компоненту с интентом.
     *
     * Комбинация этих аттрибутов используется системой, для определения того,
     * какой компонент запустить для обработки интента.
     *
     * #
     * # [PendingIntent]
     *
     * Используется для описания интента, выполнение которого отложено во времени.
     * Инстанс класса PendingIntent создается одним из статических методов:
     *
     * * [PendingIntent.getActivity] - получение PendingIntent, который запустит новое Activity
     * (как [Context.startActivity]). Обратите внимание, что Activity будет запущено вне контекста
     * существующего Activity, поэтому вы должны использовать флаг запуска
     * [Intent.FLAG_ACTIVITY_NEW_TASK] в намерении.
     *
     * * [PendingIntent.getActivities] - похож на [PendingIntent.getActivity], но действует как
     * [Context.startActivities]. Последний Intent в массиве, передаваемом в метод, будет являться
     * первичным ключем для создаваемого PendingIntent. Первый интент в массиве будет запущен вне
     * контекста существующего activity, поэтому необходимо использовать флаг [Intent.FLAG_ACTIVITY_NEW_TASK].
     * Следующее (и далее) activity будет запущенно в контексте предыдущего, поэтому данный флаг не
     * потребуется (и не желателен).
     * (Немного про то, зачем вообще нужен метод [Context.startActivities]. Нужен он для того, чтобы
     * создать искусственный (synthetic) back-stack при запуске Task-а.)
     *
     * * [PendingIntent.getBroadcast] - создание PendingIntent, который выполнит трансляцию (broadcast)
     * (like calling [Context.sendBroadcast]).
     *
     * * [PendingIntent.getService] - создание PendingIntent, который запустит [android.app.Service]
     * (как [Context.startService]).
     *
     * * Также можно создать PendingIntent при помощи метода [Activity.createPendingResult].
     * Данный PendingIntent можно куда-нибудь передать и при вызове у него [PendingIntent.send]
     * данные будут передаваться в колбек активити [Activity.onActivityResult].
     * Может пригодиться для обратной связи между Сервисом и Активити.
     *
     * Запускается PendingIntent методом [PendingIntent.send]. Отменяется методом [PendingIntent.cancel]
     * При создании в PendingIntent записывается информация о желаемом интенте и о том,
     * какой компонент будет запущен.
     * Объекты PendingIntent переживают остановку процесса,
     * поэтому система может использовать PendingIntent для старта приложения.
     * Один из частых примеров использования PendingIntent – это создание нотификации.
     * Метод NotificationCompat.Builder.setContentIntent() принимает PendingIntent,
     * который выполняется, когда пользователь кликает на нотификацию.
     *
     * Объект PendingIntent – это ссылка на токен,
     * который используется системой для получения информации об интенте.
     *
     * Для эквивалентных инстансов класса Intent создаются одинаковые объекты PendingIntent.
     * Если система уже хранит токен, идентичный созданному PendingIntent,
     * то предыдущий PendingIntent будет отменен.
     * Объекты Intent эквивалентны,
     * если имеют одинаковые action, data, category и component (см. структуру интента).
     * При этом данные extras могут различаться.
     * Сравнить два интента можно методом [Intent.filterEquals].
     * Чтобы зарегистрировать несколько объектов PendingIntent для эквивалентных интентов,
     * нужно использовать разные значения requestCode при создании PendingIntent.
     */
    @Suppress("UnusedEquals")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pending_intent_example)
        val intent1 = createIntent(action = "action 1", extra = "extra 1")
        val intent2 = createIntent(action = "action 2", extra = "extra 2")
        intent1.filterEquals(intent2) //false т.к. actions не равны
        val pendingIntent1 = PendingIntent.getActivity(this, 0, intent1, 0)
        val pendingIntent2 = PendingIntent.getActivity(this, 0, intent2, 0)
        pendingIntent1 == pendingIntent2 //false, так не равны (filterEquals) intents

        val intent3 = createIntent(action = "action", extra = "extra 1")
        val intent4 = createIntent(action = "action", extra = "extra 2")
        intent3.filterEquals(intent4) //true
        val pendingIntent3 = PendingIntent.getActivity(this, 0, intent1, 0)
        val pendingIntent4 = PendingIntent.getActivity(this, 0, intent2, 0)
        pendingIntent3 == pendingIntent4 //true, так равны (filterEquals) intents

        // Дефолтное поведение системы:
        // Если мы создали pendingIntent3, а затем
        // pendingIntent4, то в pendingIntent4 попадет intent3 (так как intent3.filterEquals(intent4) == true)

        // Повлиять на дефолтное поведение системы можно при помощи flags и requestCode,
        // передаваемых при создании PendingIntent.

        PendingIntent.FLAG_CANCEL_CURRENT
        // Если система видит, что создаваемый с таким флагом PendingIntent похож на уже существующий,
        // то она отменит (удалит) существующий.

        PendingIntent.FLAG_UPDATE_CURRENT
        // Если система видит, что создаваемый с таким флагом PendingIntent похож на существующий,
        // то она возьмет extra-данные Intent создаваемого PendingIntent и запишет их
        // вместо extra-данных Intent существующего PendingIntent.
        // Проще говоря, существующий PendingIntent будет использовать Intent из создаваемого.

        PendingIntent.FLAG_ONE_SHOT
        // PendingIntent с этим флагом сработает лишь один раз.

        PendingIntent.FLAG_NO_CREATE
        // PendingIntent не будет создан, если среди существующих нет похожего.

        // При использовании requestCode (если они отличаются) PendingIntent не будут считаться похожими
        // даже при равных Intents.

    }

    private fun createIntent(action: String, extra: String): Intent {
        return Intent(this, PendingIntentExampleActivity::class.java)
            .apply {
                setAction(action)
                putExtra("extra", extra)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
    }
}