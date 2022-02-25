package com.synthesizer.source.rawg.utils

fun Int?.isNull(): Boolean {
    return (this as? Number).isNull()
}

fun Int?.orZero(): Int {
    return (this as? Number).orZero().toInt()
}

fun Int?.orIntMin(): Int {
    return this ?: Int.MIN_VALUE
}

fun Int?.isNullOrZero(): Boolean {
    if (isNull()) return true
    else if (orZero() == 0) return true
    return false
}