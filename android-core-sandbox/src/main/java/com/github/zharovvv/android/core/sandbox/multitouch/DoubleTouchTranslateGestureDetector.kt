package com.github.zharovvv.android.core.sandbox.multitouch

import android.graphics.PointF
import android.view.MotionEvent
import android.view.MotionEvent.INVALID_POINTER_ID
import com.github.zharovvv.android.core.sandbox.multitouch.DoubleTouchTranslateGestureDetector.Pointer.PointerPool

class DoubleTouchTranslateGestureDetector(
    private val listener: OnDoubleTouchTranslateListener
) {

    var inProgress = false
        private set

    fun onTouchEvent(event: MotionEvent): Boolean {
        val pointerCount = event.pointerCount
        if (event.actionMasked == MotionEvent.ACTION_POINTER_UP) {
            val pointerId = event.getPointerId(event.actionIndex)
            PointerPool.recycle(pointerId)
        }
        if (pointerCount > 2) {
            //ignore below code
            return true
        }
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                PointerPool.obtain(
                    id = event.getPointerId(0),
                    x = event.x,
                    y = event.y
                )
            }

            MotionEvent.ACTION_POINTER_DOWN -> {
                var pointerIndex = 0
                while (pointerIndex < pointerCount) {
                    PointerPool.obtain(
                        id = event.getPointerId(pointerIndex),
                        x = event.getX(pointerIndex),
                        y = event.getY(pointerIndex)
                    )
                    pointerIndex++
                }

                if (inProgress.not()) {
                    inProgress = true
                    listener.onDoubleTouchTranslateBegin()
                }
            }

            MotionEvent.ACTION_MOVE -> {
                if (inProgress) {
                    var dxSum = 0f
                    var dySum = 0f
                    PointerPool.forEach { pointer ->
                        val pointerIndex = event.findPointerIndex(pointer.id)

                        val x = event.getX(pointerIndex)
                        val y = event.getY(pointerIndex)
                        val oldX = pointer.coords.x
                        val oldY = pointer.coords.y
                        pointer.coords.set(x, y)
                        dxSum += x - oldX
                        dySum += y - oldY
                    }
                    val dx = dxSum / pointerCount
                    val dy = dySum / pointerCount
                    listener.onDoubleTouchTranslate(dx, dy)
                }
            }

            MotionEvent.ACTION_UP -> {
                inProgress = false
                PointerPool.recycle(event.getPointerId(0))
                listener.onDoubleTouchTranslateEnd()
            }
        }
        return true
    }

    interface OnDoubleTouchTranslateListener {
        fun onDoubleTouchTranslate(dx: Float, dy: Float)

        fun onDoubleTouchTranslateBegin() {
        }

        fun onDoubleTouchTranslateEnd() {
        }
    }

    private class Pointer private constructor(
        id: Int,
        val coords: PointF
    ) {
        var id: Int = id
            private set

        private var shouldBeRecycled: Boolean = true
        private var next: Pointer? = null

        override fun toString(): String {
            return "Pointer(id = $id, coords = $coords, shouldBeRecycled = $shouldBeRecycled, next = $next)"
        }

        object PointerPool : Iterator<Pointer>, Iterable<Pointer> {

            private var head: Pointer = Pointer(
                id = INVALID_POINTER_ID,
                coords = PointF()
            )
            private var tail: Pointer = Pointer(
                id = INVALID_POINTER_ID,
                coords = PointF()
            )

            init {
                head.next = tail
            }

            fun obtain(id: Int, x: Float, y: Float): Pointer {
                val recycledPointer = tail
                swap()
                return recycledPointer.apply {
                    this.id = id
                    coords.set(x, y)
                    shouldBeRecycled = false
                }
            }

            private fun swap() {
                val newTail = head
                val newHead = tail
                head = newHead
                tail = newTail
                head.next = tail
                tail.next = null
            }

            fun recycle(id: Int) {
                val pointer = find { it.id == id } ?: return
                if (pointer == head && tail.shouldBeRecycled.not()) {
                    swap()
                }
                pointer.apply {
                    this.id = INVALID_POINTER_ID
                    shouldBeRecycled = true
                }
            }

            //region iterator
            private var currentPointerOnIterator: Pointer? = head

            override fun iterator(): Iterator<Pointer> = also { currentPointerOnIterator = head }

            override fun hasNext(): Boolean {
                val current = currentPointerOnIterator ?: return false
                return current.shouldBeRecycled.not()
            }

            override fun next(): Pointer {
                val pointer = currentPointerOnIterator!!
                currentPointerOnIterator = pointer.next
                return pointer
            }
            //endregion

            override fun toString(): String = buildString {
                append('[')
                append("head = $head")
                append(',')
                append("tail = $tail")
                append(']')
            }
        }
    }
}