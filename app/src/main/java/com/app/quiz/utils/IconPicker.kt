package com.app.quiz.utils

import com.app.quiz.R

object IconPicker{
    val icons = arrayOf(
        R.drawable.ic_icon_01,
        R.drawable.ic_icon_02,
        R.drawable.ic_icon_03,
        R.drawable.ic_icon_04,
        R.drawable.ic_icon_05,
        R.drawable.ic_icon_06,
        R.drawable.ic_icon_07,
        R.drawable.ic_icon_08,
    )
    var currentIcon  = 0

    fun getIcon(): Int {
        currentIcon = (currentIcon + 1) % icons.size
        return icons[currentIcon]
    }
}