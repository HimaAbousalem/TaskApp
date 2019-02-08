package com.apps.abousalem.todoapptask.utils

import java.util.*

object DateUtility{

    fun calculateDate(oldDate: Date): String{
        val newDate = Date()
        val diff = newDate.time - oldDate.time
        val seconds = diff / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24
        return when {
            (seconds<60) -> "$seconds seconds ago."
            (minutes <60) -> "$minutes minutes ago."
            (hours< 24) -> "$hours hours ago."
            (days >1) -> "$days days ago."
            else -> ""
        }
    }
}