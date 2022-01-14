package com.himbrhms.checkapp.ui.theme

import androidx.compose.ui.graphics.Color

val Color.Companion.DesertSand
    get() = Color(0xFFEDC9AF)

val Color.Companion.LightDesertSand
    get() = Color(0xFFfcf1e2)

val Color.Companion.DaySkyBlue
    get() = Color(0xFF82CAFF)

val Color.Companion.LightCyan
    get() = Color(0xFFE0FFFF)

val Color.Companion.MagicMind
    get() = Color(0xFFAAF0D1)

val Color.Companion.Cream
    get() = Color(0xFFFFFFCC)

val Color.Companion.PigPink
    get() = Color(0xFFFDD7E4)

val Color.Companion.EggShell
    get() = Color(0xFFFFF9E3)

val Color.Companion.LightCoral
    get() = Color(0xFFF08080)

val Color.Companion.PleassantWhite
    get() = Color(0xffeeeeee)

fun colorFromLong(value: Long) = Color(value.toULong())

val Color.longValue
    get() = value.toLong()