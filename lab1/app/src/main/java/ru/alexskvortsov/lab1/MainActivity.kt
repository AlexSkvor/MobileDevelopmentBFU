package ru.alexskvortsov.lab1

import android.content.Intent
import android.os.Bundle

class MainActivity : LoggingLifecycleActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startForegroundService(Intent(this, ServiceWithActivityStarting::class.java))
    }
}
