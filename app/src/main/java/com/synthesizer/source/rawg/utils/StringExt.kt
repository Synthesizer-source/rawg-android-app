package com.synthesizer.source.rawg.utils

private val months = listOf(
    "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
)

fun String.convertToDate(): String {
    /*
    * 0 -> year
    * 1 -> month
    * 2 -> day
    * */
    val date = this.split(delimiters = arrayOf("-"))
    return "${date[2]} ${months[date[1].toInt() - 1]}, ${date[0]}"
}