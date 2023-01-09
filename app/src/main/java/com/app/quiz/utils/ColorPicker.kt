package com.app.quiz.utils

object ColorPicker{
    val colors = arrayOf(
        "#8d31b8",
        "#69ac99",
        "#58c3c3",
        "#a44290",
        "#dd6043",
        "#907dca",
        "#fc7c43",
        "#4f7e38",
        "#7efcb1",
        "#cd061f",
        "#9c3917",
        "#df7d06",
        "#ba2d37",
    )
    var currentColorIndex = 0

    fun getColor(): String  {
        currentColorIndex = (currentColorIndex + 1) % colors.size
        return colors[currentColorIndex]
    }
}