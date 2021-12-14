package com.github.zharovvv.android.core.sandbox.di.example

data class Processor(val name: String)

data class MotherBoard(val name: String)

data class RAM(val size: String)

data class Computer(
    val processor: Processor,
    val motherBoard: MotherBoard,
    val ram: RAM
)