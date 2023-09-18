package ru.alexskvortsov.lab1

import android.content.Intent
import android.os.Bundle
import android.view.View

class SingleInstanceActivity2 : LoggingLifecycleActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        findViewById<View>(R.id.textViewMain)?.setOnClickListener {
            startActivity(Intent(this, SingleInstanceActivity2::class.java))
        }
    }
}
