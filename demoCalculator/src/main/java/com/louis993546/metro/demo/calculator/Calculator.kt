package com.louis993546.metro.demo.calculator

interface Calculator {
    val smallDisplay: String?
    val bigDisplay: String

    fun digit(i: Int)

    fun decimal()

    fun operation(op: Operation)

    enum class Operation {
        Plus, Minus, Multiplier, Divide, Equal,
        // TODO should these all just be operation? or should i split it out to sth else?
        C, MC, MR, Mplus, Backspace
    }
}
