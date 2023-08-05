package com.github.zharovvv.animationsandbox.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.zharovvv.animationsandbox.databinding.FragmentDeepDiveIntoAnimationBinding

/**
 * Тут будет подробное описание про Choreographer.
 * И про то, как работают анимации под капотом.
 *
 * - [Про отрисовку первого кадра в Android](https://habr.com/ru/articles/522670/)
 * - [Как происходит отрисовка в Android](https://www.youtube.com/watch?v=zdQRIYOST64)
 * ## Экстракт всего видео:
 * Есть такая штука как Choreographer и она обычно срабатывает 60 раз в секунду.
 * И говорит, эй VSync, это интервал, с которым кадр синхронизируется.
 *
 * Инвалидация любой вьюхи в конечном итоге приводит к следующему:
 * ```
 * View.invalidate -> ViewParent.invalidateChild() -> ... -> DecorView.invalidateChild() ->
 * ViewRootImpl.invalidateChild() -> ViewRootImpl.scheduleTraversals()
 * (планирует обход дерева - кладет задачу в очередь (через Choreographer) - в конечном итоге приводит
 * к Handler.enqueueMessage(...))
 * ViewRootImpl.performTraversals() -> ViewRootImpl.performDraw() -> ViewRootImpl.draw() -> ... ->
 * View.draw() -> View.onDraw(Canvas)
 * Метод draw в конечном итоге заканчивается оптимизацией, которую добавили в Honeycomb
 * ```
 * DisplayList - оптимизация метода View.draw() (а точнее как работает Canvas под капотом),
 * которую добавили в Honeycomb.
 * Это компактный способ представления операций типа Canvas.drawDrawable, drawBackgrounds, drawLine.
 * (Этот код я не нашел. Очень похоже, что это происходит под капотом в нативной реализации методов
 * Canvas)
 * ```
 * //TextView
 * protected void onDraw(Canvas canvas) {
 *      canvas.drawRect();
 *      canvas.drawText();
 * }
 * ```
 * превращается в некое представление View в виде DisplayList
 * ```
 * DisplayList {
 *      rect(bounds, color)
 *      text("Item 2")
 * }
 * ```
 * Таким образом вся иерархия вьюх - превращается в иерархию DisplayList (со всей необходимой информацией)
 * ```
 * DisplayList {
 *      DisplayList {
 *          ...
 *          DisplayList {
 *              rect(bounds, color)
 *              text("Item 2")
 *          }
 *      }
 * }
 * ```
 * Все, что нам нужно было в UI потоке, это создать иерархию DisplayList.
 * Далее нужно синхронизировать (Sync) эту информацию с render-thread.
 * RenderThread - поток, который общается только с GPU (графическим процессором).
 * Далее происходит куча всяких оптимизаций над DisplayList.
 * Например, похожие операции группируются вместе, чтобы отрисовка происходила более эффективно.
 *
 * [Ещё про рендеринг](https://android-developers.googleblog.com/2020/04/high-refresh-rate-rendering-on-android.html)
 * ## Конец
 */
class DeepDiveIntoAnimationFragment : Fragment() {

    private var _binding: FragmentDeepDiveIntoAnimationBinding? = null
    private val binding: FragmentDeepDiveIntoAnimationBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentDeepDiveIntoAnimationBinding.inflate(inflater, container, false)
        .also { _binding = it }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}