package ru.alexskvortsov.lab1

import android.net.Uri
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : LoggingLifecycleActivity() {

    companion object {
        const val IMAGE_URI_KEY = "IMAGE_URI_KEY_SecondActivity"
        const val NAME_KEY = "NAME_KEY_SecondActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        setupFromIntent()
    }

    private fun setupFromIntent() {
        val imageUri = intent.extras?.getParcelable(IMAGE_URI_KEY) as? Uri
        val name = intent.extras?.getString(NAME_KEY)

        imageUri?.let { image.setImageURI(it) }
        name?.let { userName.text = it }
    }
}