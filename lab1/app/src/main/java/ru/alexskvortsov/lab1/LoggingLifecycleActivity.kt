package ru.alexskvortsov.lab1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class LoggingLifecycleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        alsoPrintDebug("onCreate")
    }

    override fun onStart() {
        super.onStart()
        alsoPrintDebug("onStart")
    }

    override fun onRestart() {
        super.onRestart()
        alsoPrintDebug("onRestart")
    }

    override fun onResume() {
        super.onResume()
        alsoPrintDebug("onResume")
    }

    override fun onPause() {
        super.onPause()
        alsoPrintDebug("onPause")
    }

    override fun onStop() {
        super.onStop()
        alsoPrintDebug("onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        alsoPrintDebug("onDestroy")
    }
}