package com.github.zharovvv.android.core.sandbox.custom.view

import kotlin.random.Random

private val random = Random //same Random.Default
const val MAX_VOLUME = 100

fun getWaveData(): IntArray {
    return IntArray(200) { random.nextInt(MAX_VOLUME) }
}