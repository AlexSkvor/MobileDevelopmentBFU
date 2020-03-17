package ru.alexskvortsov.lab1

import android.content.Context
import android.content.Intent
import android.os.Bundle

class SecondActivity : LoggingLifecycleActivity() {

    companion object {
        fun newIntent(context: Context, boolArray: List<Boolean>, arrayIndex: Int): Intent {
            val intent = Intent(context, SecondActivity::class.java)
            intent.putExtra("extraArray", boolArray.toBooleanArray())
            intent.putExtra("extraSize", arrayIndex)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val array = intent.extras?.getBooleanArray("extraArray")?.toList()
        val size = intent.extras?.getInt("extraSize")

        array.alsoPrintDebug("AAAAAAAA")
        size.alsoPrintDebug("BBBBBBBBB")
    }

}