package com.github.zharovvv.android.core.sandbox.custom.view.util

import org.junit.Test

class LinearInterpolationKtTest {

    @Test
    fun interpolateArray_ifCorrectSource_shouldReturnCorrectDestination() {
        val source: IntArray = intArrayOf(5, 10, 20, 5, 10, 5, 10, 5, 20, 10)
        val result = interpolateArray(source, 5)
        assertEqualsIntArray(intArrayOf(7, 12, 7, 7, 15), result)
    }

    private fun assertEqualsIntArray(expected: IntArray, actual: IntArray) {
        if (expected.size != actual.size) {
            throw AssertionError()
        }
        for (i in expected.indices) {
            if (expected[i] != actual[i]) {
                throw AssertionError("\nexpected[$i] = ${expected[i]}\nactual[$i] = ${actual[i]}")
            }
        }
    }
}