package ru.alexskvortsov.lab1

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.StringBuilder

class MainActivity : LoggingLifecycleActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        text.text = getMacAddress()
        val phone = "+7 (960) 454 343 88"
        text.text = phone.replaceCharsByCondition(
            with = '#',
            startIndexInclusive = 0,
            endIndexExclusive = phone.length,
            condition = { it.isDigit() }
        )
    }

    private fun String.replaceCharsByCondition(
        with: Char,
        startIndexInclusive: Int,
        endIndexExclusive: Int,
        condition: (Char) -> Boolean
    ): String {
        val newStringBuilder = StringBuilder()
        forEachIndexed { i, char ->
            if (condition(char) && i >= startIndexInclusive && i < endIndexExclusive) {
                newStringBuilder.append(with)
            } else {
                newStringBuilder.append(char)
            }
        }
        return newStringBuilder.toString()
    }
}
