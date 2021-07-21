package com.github.zharovvv.android.core.sandbox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle

/**
 * Основными компонентами приложения Android являются:
 * * Activity
 * * Service
 * * Broadcast receiver
 * * Content provider
 *
 * # Activity
 * __Activity__ - Это компонент приложения, который представляет собой экран,
 * с которым пользователи могут взаимодействовать для выполнения каких-либо действий,
 * например набрать номер телефона, сделать фото, отправить письмо или просмотреть карту.
 * Каждой Activity присваивается окно для прорисовки соответствующего пользовательского интерфейса.
 *
 * Активность имеет жизненный цикл — начало, когда Android создает экземпляр активности,
 * промежуточное состояние, и конец, когда экземпляр уничтожается системой и освобождает ресурсы.
 * Активность может находиться в трех состояниях:
 *
 * * активная (active или running (Created → Started → Resumed)) — активность находится на переднем плане экрана.
 * Пользователь может взаимодействовать с активным окном;
 * * приостановленная (paused) — активность потеряла фокус, но все еще видима пользователю.
 * То есть активность находится сверху и частично перекрывает данную активность.
 * Приостановленная активность может быть уничтожена системой в критических ситуациях при нехватке памяти;
 * * остановленная (stopped) — если данная активность полностью закрыта другой активностью.
 * Она больше не видима пользователю и может быть уничтожена системой,
 * если память необходима для более важного процесса.
 *
 * Если активность, которая была уничтожена системой, нужно снова показать на экране,
 * она должна быть полностью перезапущена и восстановлена в своем предыдущем состоянии.
 *
 * [Жизненный цикл приложения на Android](http://developer.alexanderklimov.ru/android/images/activity_lifecycle.png)
 *
 * * Запуск приложения
 * onCreate() → onStart() →  onResume()
 *
 * * Нажимаем кнопку Назад для выхода из приложения
 * onPause() → onStop() → onDestroy()
 *
 * * Нажата кнопка Домой
 * onPause() → onStop()
 *
 * * После нажатия кнопки Домой, когда приложение запущено из списка недавно открытых приложений или через значок
 * onRestart() → onStart() → onResume()
 *
 * * Когда запускается другое приложение из области уведомлений или открывается приложение Настройки
 * onPause() → onStop()
 *
 * * Нажата кнопка Назад в другом приложении или в Настройках и ваше приложение стало снова видимым.
 * onRestart() → onStart() → onResume()
 *
 * * Открывается диалоговое окно
 * onPause()
 *
 * * Диалоговое окно закрывается
 * onResume()
 *
 * * Кто-то звонит на телефон
 * onPause() → onResume()
 *
 * * Пользователь отвечает на звонок
 * onPause()
 * * Разговор окончен
 * onResume()
 * * Экран телефона гаснет
 * onPause() → onStop()
 * * Экран снова включён
 * onRestart() → onStart() → onResume()
 *
 * На китайских планшетах иногда наблюдал, когда какие-то методы не срабатывали.
 *
 * * При повороте активность проходит через цепочку различных состояний. Порядок следующий.
 * onPause() → onStop() → onDestroy() → onCreate() → onStart() → onResume()
 */
class MainActivity : AppCompatActivity() {

    /**
     * Метод onCreate() вызывается при создании или перезапуска активности.
     * Система может запускать и останавливать текущие окна в зависимости от происходящих событий.
     * Внутри данного метода настраивают статический интерфейс активности.
     * Инициализирует статические данные активности, связывают данные со списками и т.д.
     * Связывает с необходимыми данными и ресурсами.
     * Задаёт внешний вид через метод setContentView().
     * В этом методе загружайте пользовательский интерфейс, размещайте ссылки на свойства класса,
     * связывайте данные с элементами управления, создавайте сервисы и потоки.
     * Метод onCreate() принимает объект Bundle, содержащий состояние пользовательского интерфейса,
     * сохранённое в последнем вызове обработчика onSaveInstanceState.
     * Для восстановления графического интерфейса в его предыдущем состоянии нужно задействовать
     * эту переменную: внутри onCreate() или переопределив метод onRestoreInstanceState().
     * Операции по инициализации, занимающие много времени,
     * следует выполнять в фоновом процессе, а не с помощью метода onCreate().
     * В противном случае можно получить диалоговое окно ANR
     * (Application Not Responding, приложение не отвечает).
     * В методе можно сделать проверку, запущено ли приложение впервые или восстановлено из памяти.
     * Если значение переменной savedInstanceState будет null, приложение запускается первый раз.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /**
     * За onCreate() всегда следует вызов onStart(),
     * но перед onStart() не обязательно должен идти onCreate(), так как onStart() может вызываться
     * и для возобновления работы приостановленного приложения
     * (приложение останавливается методом onStop()).
     * При вызове onStart() окно ещё не видно пользователю, но вскоре будет видно.
     * Вызывается непосредственно перед тем, как активность становится видимой пользователю.
     * Сопровождается вызовом метода onResume(), если активность получает передний план,
     * или вызовом метода onStop(), если становится скрытой.
     */
    override fun onStart() {
        super.onStart()
    }

    /**
     * Метод onResume() вызывается после onStart(),
     * даже когда окно работает в приоритетном режиме и пользователь может его наблюдать.
     * В этот момент пользователь взаимодействует с созданным вами окном.
     * Приложение получает монопольные ресурсы.
     * Запускает воспроизведение анимации, аудио и видео. Также может вызываться после onPause().
     * Имейте в виду, что система вызывает этот метод каждый раз,
     * когда ваша активность идёт на переднем плане, в том числе, при первом создании.
     * Таким образом, вы должны реализовать onResume() для инициализации компонентов,
     * регистрации любых широковещательных приёмников или других процессов,
     * которые вы освободили/приостановили в onPause() и выполнять любые другие инициализации,
     * которые должны происходить, когда активность вновь активна.
     * __Пытайтесь размещать относительно быстрый и легковесный код__,
     * чтобы ваше приложение оставалось отзывчивым при скрытии с экрана или выходе на передний план.
     * Вам не нужно перезагружать состояние пользовательского интерфейса внутри него,
     * так как эти функции возложены на обработчики onCreate() и onRestoreInstanceState.
     */
    override fun onResume() {
        super.onResume()
    }

    /**
     * Когда пользователь решает перейти к работе с новым окном,
     * система вызовет для прерываемого окна метод onPause().
     * По сути происходит свёртывание активности. Сохраняет незафиксированные данные.
     * Деактивирует и выпускает монопольные ресурсы.
     * Останавливает воспроизведение видео, аудио и анимацию.
     * От onPause() можно перейти к вызову либо onResume(), либо onStop().
     * В этом методе необходимо остановить анимацию и другие действия,
     * которые загружают процессор. Зафиксировать несохранённые данные, например, черновик письма,
     * потому как после его выполнения работа активности может прерваться без предупреждения.
     * Освободить системные ресурсы, например, обработку данных от GPS.
     * __Пытайтесь размещать относительно быстрый и легковесный код__,
     * чтобы ваше приложение оставалось отзывчивым при скрытии с экрана или выходе на передний план.
     *
     * Исходя из архитектуры своего приложения, вы также можете приостановить выполнение потоков,
     * процессов или широковещательных приёмников, пока активность не появится на переднем плане.
     */
    override fun onPause() {
        super.onPause()
    }

    /**
     * Метод onStop() вызывается, когда окно становится невидимым для пользователя.
     * Это может произойти при её уничтожении,
     * или если была запущена другая активность (существующая или новая),
     * перекрывшая окно текущей активности.
     * Всегда сопровождает любой вызов метода onRestart(), если активность возвращается,
     * чтобы взаимодействовать с пользователем, или метода onDestroy(),
     * если эта активность уничтожается.
     * Когда ваша активность останавливается, объекты активности хранятся в памяти
     * и восстанавливаются, когда активность возобновляет свою работу.
     * Вам не нужно повторно инициализировать компоненты, которые были созданы ранее.
     * Кроме того, система отслеживает текущее состояние для каждого представления, поэтому,
     * если пользователь введёт текст в текстовое поле, то его содержание сохраняется и
     * вам не нужно сохранять и восстанавливать его.
     *
     * _Примечание: Даже если система закрыла вашу активность, когда она была остановлена,
     * она по-прежнему сохраняет состояние объектов, таких как текст в EditText
     * в специальном объекте Bundle (в виде ключ-значение) и восстанавливает их
     * , если пользователь переходит обратно к тому же экземпляру активности._
     *
     * В этом методе можно сделать сложные операции по сохранению данных:
     * для приостановки сложной анимации, потоков, отслеживания показаний датчиков,
     * запросов к GPS, таймеров, сервисов или других процессов,
     * которые нужны исключительно для обновления пользовательского интерфейса.
     * Нет смысла потреблять ресурсы (такты центрального процессора или сетевой трафик)
     * для обновления интерфейса, в то время как он не виден на экране.
     * Примените методы onStart() или onRestart() для возобновления или повторного запуска этих
     * процессов, когда активность опять станет видимой.
     * При нехватке памяти система может уничтожить скрытую активность,
     * минуя метод onStop() с вызовом метода onDestroy().
     */
    override fun onStop() {
        super.onStop()
    }

    /**
     * Если окно возвращается в приоритетный режим после вызова onStop(),
     * то в этом случае вызывается метод onRestart().
     * Т.е. вызывается после того, как активность была остановлена
     * и снова была запущена пользователем. Всегда сопровождается вызовом метода onStart().
     *
     * onRestart предшествует вызовам метода onStart() (кроме самого первого).
     * Используйте его для специальных действий,
     * которые должны выполняться только при повторном запуске активности в рамках «полноценного» состояния.
     */
    override fun onRestart() {
        super.onRestart()
    }

    /**
     * Метод вызывается по окончании работы активности, при вызове метода finish() или в случае,
     * когда система уничтожает этот экземпляр активности для освобождения ресурсов.
     * Эти два сценария уничтожения можно определить вызовом метода isFinishing().
     * Вызывается перед уничтожением активности. Это последний запрос,
     * который получает активность от системы.
     * Если определённое окно находится в верхней позиции в стеке,
     * но невидимо пользователю и система решает завершить это окно, вызывается метод onDestroy().
     * В этом случае метод удаляет все статические данные активности. Отдаёт все используемые ресурсы.
     */
    override fun onDestroy() {
        super.onDestroy()
    }

    //------------------------Конец основных методов жизненного цикла-------------------------------

    /**
     * Данный метод вызывается перед тем, как Activity будет уничтожена (перед вызовом [onDestroy]).
     * Параметром метода является Bundle, в который мы будем складывать необходимые для сохранения данные.
     * Рекомендуется сохранять данным способом информацию, объём которой не превышает 1 мегабайт,
     * в случае превышения лимита мы получим ошибку TransactionTooLargeException.
     *
     * Если метод не переопределяется, то будет создан объект [Bundle.EMPTY].
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
    }

    /**
     * Вызывается после метода [onStart].
     * В данный метод мы получим наш Bundle, в котором была сохранена информация.
     */
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onRestoreInstanceState(savedInstanceState, persistentState)
    }
}