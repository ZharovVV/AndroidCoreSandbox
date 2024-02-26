package com.github.zharovvv.core.ui.gestures

import android.view.MotionEvent

val MotionEvent.actionString: String get() = actionMasked.asActionString()

val MotionEvent.debugInfo: String
    get() = buildString {
        //Получаем action без pointerIndex, применяя специальную маску к Int.
        val maskedAction = actionMasked
        append("action = ${actionString};")
        appendLine()
        if (maskedAction == MotionEvent.ACTION_POINTER_UP || maskedAction == MotionEvent.ACTION_POINTER_DOWN) {
            //pointerIndex может меняться по ходу жеста.
            // Но есть также pointer ID, который всегда остается постоянным.
            append("pointerIndex = $actionIndex;")
            appendLine()
        }
        var index = 0
        while (index < pointerCount) {
            append(
                "pIndex = $index; " +
                        "pointerId = ${getPointerId(index)}; " +
                        "x = ${getX(index)}; " +
                        "y = ${getY(index)}"
            )
            appendLine()
            index++
        }
    }

private enum class MotionEventAction(
    val code: Int?
) {
    ACTION_DOWN(0),
    ACTION_UP(1),
    ACTION_MOVE(2),
    ACTION_CANCEL(3),
    ACTION_OUTSIDE(4),
    ACTION_POINTER_DOWN(5),
    ACTION_POINTER_UP(6),
    ACTION_HOVER_MOVE(7),
    ACTION_SCROLL(8),
    ACTION_HOVER_ENTER(9),
    ACTION_HOVER_EXIT(10),
    OTHER(null)
}

private fun Int.asActionString(): String =
    MotionEventAction.values().find { it.code == this }?.toString() ?: "OTHER(code = $this)"