package com.github.zharovvv.android.core.sandbox.custom.view.util

import kotlin.math.ceil

fun interpolateArray(source: IntArray, destinationLength: Int): IntArray {
    val destination = IntArray(destinationLength)
    val rangeSize = ceil(source.size.toFloat() / destination.size.toFloat()).toInt()
    for (i in destination.indices) {
        destination[i] = interpolate(source, i * rangeSize, i * rangeSize + rangeSize - 1)
    }
    return destination
}

private fun interpolate(source: IntArray, start: Int, end: Int): Int {
    val realEnd = if (end > source.size - 1) {
        source.size - 1
    } else {
        end
    }
    if (realEnd < start) {
        return 0
    }
    var count = 0
    for (i in start..realEnd) {
        count += source[i]
    }
    return count / (realEnd - start + 1)
}