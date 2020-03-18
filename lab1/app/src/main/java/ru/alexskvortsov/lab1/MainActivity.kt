package ru.alexskvortsov.lab1

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : LoggingLifecycleActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imageView.colorFilter = ColorMatrixColorFilter(ColorMatrix().apply { setSaturation(0f)})
    }
}
