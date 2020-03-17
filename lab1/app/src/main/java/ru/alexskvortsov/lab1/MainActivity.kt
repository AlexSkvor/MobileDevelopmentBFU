package ru.alexskvortsov.lab1

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : LoggingLifecycleActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        testButton.setOnClickListener {
            val list = listOf(true, false, false, true, false)
            val intent = SecondActivity.newIntent(applicationContext, list, list.size)
            startActivity(intent)
        }
    }
}
