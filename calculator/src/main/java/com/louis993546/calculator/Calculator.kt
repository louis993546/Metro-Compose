package com.louis993546.calculator

import kotlinx.coroutines.flow.Flow

internal interface Calculator {
    val display: Flow<Display>

    fun digit(i: Int)

    fun decimal()

    fun operation(op: Operation)

    enum class Operation {
        Plus, Minus, Multiplier, Divide, Equal,
        // TODO should these all just be operation? or should i split it out to sth else?
        C, MC, MR, Mplus, Backspace
    }
}

data class Display(
    val big: String,
    val small: String?,
) {
    companion object {
        val empty = Display(big = "0", small = null)
    }
}
