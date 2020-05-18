package ru.alexskvortsov.lab1

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : LoggingLifecycleActivity() {

    companion object {
        private const val REQUEST_IMAGE_CAPTURE = 1
    }

    private var savedPath: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        photoButton.setOnClickListener {
            if (name.text.toString().isBlank()) Toast.makeText(this, R.string.youDidNotWriteName, Toast.LENGTH_LONG).show()
            else dispatchTakePictureIntent()
        }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                val file = createImageFile()
                val photoUri = FileProvider.getUriForFile(this, "ru.alexskvortsov.lab1.fileprovider", file)
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    private fun startSecondActivity() {
        val intent = Intent(applicationContext, SecondActivity::class.java)
        val bundle = Bundle()
        bundle.putParcelable(SecondActivity.IMAGE_URI_KEY, savedPath)
        bundle.putString(SecondActivity.NAME_KEY, name.text.toString())
        intent.putExtras(bundle)
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK)
            startSecondActivity()
    }

    @SuppressLint("SimpleDateFormat")
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).also {
            savedPath = it.toUri()
        }
    }
}
