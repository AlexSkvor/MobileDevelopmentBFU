package ru.alexskvortsov.lab1

import android.os.Bundle

class MainActivity : LoggingLifecycleActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
