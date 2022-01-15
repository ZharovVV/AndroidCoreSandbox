package com.github.zharovvv.android.core.sandbox.window

import android.graphics.Paint
import android.os.Build
import android.os.Bundle
import android.view.SurfaceHolder
import com.github.zharovvv.android.core.sandbox.LogLifecycleAppCompatActivity
import com.github.zharovvv.android.core.sandbox.R

class WindowExampleActivity : LogLifecycleAppCompatActivity() {

    /**
     * # Window
     * Window – это абстрактный класс, который не является наследником Activity, Fragment или View.
     * Класс Window контролирует что и как рисуется на экране.
     * Активити имеет один инстанс Window, который можно получить методом getWindow().
     * Window, в свою очередь, имеет объект Surface и единственную иерархию View.
     * Android-приложение использует WindowManager для создания объектов типа Window и Surface,
     * на котором рисуется контент Window.
     * Когда UI должен обновиться, на объекте Surface вызывается метод lockCanvas(),
     * который возвращает объект типа Canvas.
     * Canvas передается вниз по иерархии View, ассоциированной с Window,
     * и каждая view рисует себя на канвасе.
     *
     * #
     * Рассмотрим, что происходит под капотом:
     * * Как и где создается Window
     * * Что такое DecorView. Где создается?
     * * Что происходит при вызове setContentView?
     * * Что такое WindowManager?
     * * Что такое ViewRootImpl?
     * * Что такое Surface и SurfaceHolder?
     *
     * ## 1. Создание объекта Window:
     * * (Что-то) вызывает ActivityThread#handleLaunchActivity ->
     * * -> ActivityThread#performLaunchActivity
     * (Тут инициализируется appContext, создается объект Activity и Application,
     * затем выызвается Activity#attach, о котором пойдет речь дальше,
     * затем Instrumentation#callActivityOnCreate -> Activity#performCreate -> Activity#onCreate.)
     * * -> Activity#attach.
     * Собственно в этом методе и происходит создание объекта Window:
     * mWindow = new PhoneWindow(this, window, activityConfigCallback);
     *
     * ## 2. Что такое DecorView. Где создается?
     * __public class DecorView extends FrameLayout implements RootViewSurfaceTaker, WindowCallbacks__
     * DecorView - корневая view в иерархии
     * Обычно DecorView -> LinearLayout -> (ViewStub и FrameLayout) -> ContentFrameLayout ->
     * и вот уже здесь ViewGroup, которую мы обяъявили в xml.
     * (В нашем случае так как мы вызываем
     * takeSurface (и при этом не вызываем отдельно window.getDecorView) иерархии view не будет -
     * мы напрямую рисуем на Surface.)
     *
     * Создание объекта DecorView происходит при вызове метода [setContentView].
     * * AppCompatActivity#setContentView
     * * -> AppCompatDelegateImpl#setContentView
     * * -> AppCompatDelegateImpl#ensureSubDecor
     * * -> AppCompatDelegateImpl#createSubDecor
     * В этом методе создается ViewGroup subDecor;
     * затем вызывается
     * mWindow.getDecorView(); - тут создается DecorView;
     * затем вызывается
     * // Now set the Window's content view with the decor
     * mWindow.setContentView(subDecor); - subDecor добавляется в иерархию вьюх (в DecorView).
     * * На этом этапе все вьюхи проинициализированы, но клиент пока ничего не видит.
     *
     * ## 3. Что такое WindowManager? Где он инициализируется?
     * [android.view.WindowManager] - Интерфейс, который приложения используют для общения
     * с оконным менеджером. (WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE)
     * Каждый экземпляр оконного менеджера привязан к определенному [android.view.Display].
     *
     * ##
     * __Немного про Display__.
     * Предоставляет информацию о размере и плотности логического дисплея.
     * Область отображения описывается двумя разными способами.
     * * Область отображения приложения - определяет часть экрана, которая может содержать окно приложения,
     * за исключением системных украшений. Область отображения приложения может быть меньше,
     * чем реальная область отображения, поскольку система вычитает пространство,
     * необходимое для элементов декора, таких как status bar.
     * Используйте WindowMetrics.getBounds() для запроса границ окна приложения.
     * * Реальная область отображения определяет часть экрана, которая содержит контент,
     * включая системные украшения. Даже в этом случае реальная область отображения может быть меньше,
     * чем физический размер дисплея, если оконный менеджер эмулирует меньший дисплей,
     * используя (adb shell wm size). Используйте следующие методы для запроса реальной области
     * отображения: getRealSize, getRealMetrics.
     * Логический дисплей не обязательно представляет конкретное физическое устройство отображения,
     * такое как внутренний дисплей или внешний дисплей.
     * Содержимое логического дисплея может быть представлено на одном или нескольких физических
     * дисплеях в зависимости от того, какие устройства в данный момент подключены и включено ли зеркалирование.
     * ##
     *
     * WindowManager сетится в PhoneWindow в методе Activity#attach практически сразу после создания
     * экземпляра window. Сам WindowManager создается системой, мы лишь запрашиваем инстанс оконного
     * менеджера у системы:
     * ```
     *  mWindow.setWindowManager(
     *      (WindowManager)context.getSystemService(Context.WINDOW_SERVICE),
     *      mToken, mComponent.flattenToString(),
     *      (info.flags & ActivityInfo.FLAG_HARDWARE_ACCELERATED) != 0);
     * ```
     *
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val paint = Paint().apply {
            color = resources.getColor(R.color.blue, theme)
        }
        val initX = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            windowManager.maximumWindowMetrics.bounds.centerX().toFloat()
        } else {
            windowManager.defaultDisplay.width.toFloat() / 2
        }
        val initY = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            windowManager.maximumWindowMetrics.bounds.centerY().toFloat()
        } else {
            windowManager.defaultDisplay.height.toFloat() / 2
        }
        val surfaceColor = resources.getColor(R.color.purple_200, theme)
        window.takeSurface(object : SurfaceHolder.Callback2 {

            override fun surfaceCreated(holder: SurfaceHolder) {
            }

            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int
            ) {
                val surface = holder.surface
                val canvas = holder.lockCanvas()
                if (canvas != null) {
                    canvas.drawColor(surfaceColor)
                    canvas.drawCircle(initX, initY, 200.0f, paint)
                    holder.unlockCanvasAndPost(canvas)
                }
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
            }

            override fun surfaceRedrawNeeded(holder: SurfaceHolder) {
            }
        })
//        setContentView(R.layout.activity_window_example)//useless if we call window#takeSurface
    }

    /**
     * ActivityThread#handleResumeActivity
     * Тут происходит следующее:
     * * Получаем DecorView из Window.
     * * Вызывается wm.addView(decor, l); decor - DecorView; l - WindowManager.LayoutParams
     * * В некоторых случаях (например клик в поле ввода) вызывается wm.updateViewLayout(decor, l);
     * * После этого вызывается Activity#makeVisible, где mDecor.setVisibility(View.VISIBLE);
     *
     * Под капотом WindowManagerImpl#addView вызывает WindowManagerGlobal#addView.
     * И там происходит следующее:
     * ```
     * ...
     * root = new ViewRootImpl(view.getContext(), display);
     * view.setLayoutParams(wparams);
     * mViews.add(view);
     * mRoots.add(root);
     * mParams.add(wparams);
     * ...
     * root.setView(view, wparams, panelParentView, userId);
     * ...
     * ```
     * ## 4. ViewRootImpl
     * android.view.ViewRootImpl - Вершина иерархии представлений, реализующая необходимый протокол
     * между представлением и WindowManager.
     * По большей части это внутренняя деталь реализации WindowManagerGlobal.
     * (из Джава-доки к ViewRootImpl).
     * ViewRootImpl содержит ссылку mView на "истинный" корень в иерархии представлений - DecorView
     * (после вызова root.setView(view, wparams, panelParentView, userId); в WindowManagerGlobal
     * и если конечно мы не вызывали раньше window.takeSurface).
     * Что происходит в методе ViewRootImpl#setView:
     * * -> ViewRootImpl#requestLayout
     * * -> ViewRootImpl#scheduleTraversals
     * здесь обратим внимание, что вызывается
     * mTraversalBarrier = mHandler.getLooper().getQueue().postSyncBarrier();
     *
     * ##
     * __Немного подробнее про MessageQueue#postSyncBarrier__
     * Размещает барьер синхронизации в очереди сообщений Looper.
     * Обработка сообщений происходит как обычно до тех пор, пока очередь сообщений не встретит
     * опубликованный барьер синхронизации.
     * При обнаружении барьера более поздние синхронные сообщения в очереди останавливаются
     * (предотвращаются выполнение) до тех пор, пока барьер не будет снят путем вызова
     * removeSyncBarrier и указания маркера, идентифицирующего барьер синхронизации.
     * Этот метод используется для немедленной отсрочки выполнения всех последующих синхронных
     * сообщений до тех пор, пока не будет выполнено условие, освобождающее барьер.
     * Асинхронные сообщения (см. Message.isAsynchronous освобождаются от барьера
     * и продолжают обрабатываться в обычном режиме.
     * Этот вызов всегда должен сопровождаться вызовом removeSyncBarrier с тем же токеном,
     * чтобы гарантировать, что очередь сообщений возобновит нормальную работу.
     * В противном случае приложение, вероятно, зависнет!
     * Возвращает:
     * Маркер, однозначно идентифицирующий барьер.
     * Этот токен должен быть передан в removeSyncBarrier для снятия барьера.
     * ##
     *
     * * Затем внутри метода ViewRootImpl#requestLayout вызывается:
     * mChoreographer.postCallback(Choreographer.CALLBACK_TRAVERSAL, mTraversalRunnable, null);
     *
     * ##
     * __Немного про Choreographer__
     * Координирует синхронизацию анимации, ввода и рисования.
     * Хореограф получает синхронизирующие импульсы (например, вертикальную синхронизацию)
     * от display subsystem, а затем планирует выполнение работы в рамках рендеринга следующего
     * кадра отображения.
     * Приложения обычно взаимодействуют с хореографом косвенно,
     * используя абстракции более высокого уровня в структуре анимации или иерархии представлений.
     * У каждого потока Looper есть свой хореограф.
     * Другие потоки могут публиковать обратные вызовы для запуска хореографа,
     * но они будут выполняться в Looper, к которому принадлежит хореограф.
     * ##
     *
     * Если сильно упростить то мы кладем mTraversalRunnable в очередь сообщений Looper-а
     * ```
     *  final class TraversalRunnable implements Runnable {
     *      @Override
     *      public void run() {
     *          doTraversal();
     *      }
     *  }
     * ```
     * * -> ViewRootImpl#doTraversal
     * внутри вызывается mHandler.getLooper().getQueue().removeSyncBarrier(mTraversalBarrier);
     * а затем вызывается ViewRootImpl#performTraversals.
     * * -> ViewRootImpl#performTraversals
     * Метод на 1000 строк :)
     *
     * [flow chart](https://images4.programmerall.com/816/08/086c59c7d2a0a53fcfb078e916eaf818.png)
     * * -> ViewRootImpl#performMeasure
     * тут вызывается DecorView#measure
     * * -> ViewRootImpl#performLayout
     * * -> ViewRootImpl#performDraw
     * * -> ViewRootImpl#draw
     * * В результате вся цепочка вызовов приводит к вызову WindowManagerService#addWindow. (?)
     * который в свою очередь взаимодействует с SurfaceFlinger.
     */
    override fun onResume() {
        super.onResume()

    }

    /**
     *
     * Вызов Activity#onAttachedToWindow:
     * Called when the main window associated with the activity has been
     * attached to the window manager.
     *
     * Технически говоря onAttachedToWindow, вызывается после onResume
     * (и это происходит только один раз за жизненный цикл).
     * Вызов ActivityThread.handleResumeActivity добавляет DecorView к текущему WindowManger,
     * который, в свою очередь, вызовет WindowManagerGlobal.addView(), который затем проходит по всем
     * view-хам и вызовет onAttachedToWindow для каждого view.
     * onDetachedFromWindow связана с onDestroy.
     */
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
    }
}