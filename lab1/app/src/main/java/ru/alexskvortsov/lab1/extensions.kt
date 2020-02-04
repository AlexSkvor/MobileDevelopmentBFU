package ru.alexskvortsov.lab1

import timber.log.Timber

inline fun <reified T> T.alsoPrintDebug(msg: String = "") = also { Timber.e("$msg .... $this") }