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
    if (this.isBlank()) return ""
    val date = this.split(delimiters = arrayOf("-"))
    if (date.size != 3) return ""
    if (date[0].toIntOrNull().isNull() ||
        date[1].toIntOrNull().isNull() ||
        date[2].toIntOrNull().isNull()
    ) return ""
    if (date[0].length != 4 || date[1].length != 2 || date[2].length != 2) return ""
    if (date[1].toInt() > 12) return ""
    return "${date[2]} ${months[date[1].toInt() - 1]}, ${date[0]}"
}