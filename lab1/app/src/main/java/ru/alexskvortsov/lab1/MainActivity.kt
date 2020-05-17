package ru.alexskvortsov.lab1

import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : LoggingLifecycleActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        plus.setOnClickListener { onOperationChosen(Operation.PLUS) }
        minus.setOnClickListener { onOperationChosen(Operation.MINUS) }
        divide.setOnClickListener { onOperationChosen(Operation.DIVIDE) }
        multiply.setOnClickListener { onOperationChosen(Operation.MULTIPLY) }
    }

    private fun error(@StringRes textRes: Int) {
        result.setText(textRes)
        showSnackbar(getString(textRes))
        result.setTextColor(ContextCompat.getColor(this, R.color.colorAccent))
    }

    enum class Operation { PLUS, MINUS, DIVIDE, MULTIPLY }

    private fun onOperationChosen(operation: Operation) {
        val first = firstEdit.text.toString().toIntOrNull()
            ?: return error(R.string.notEnteredFirstNumber)

        val second = secondEdit.text.toString().toIntOrNull()
            ?: return error(R.string.notEnteredSecondNumber)

        if (operation == Operation.DIVIDE && second == 0)
            return error(R.string.secondNumberIsZero)

        result.setTextColor(ContextCompat.getColor(this, R.color.black))
        when (operation) {
            Operation.PLUS -> result.text = (first + second).toString()
            Operation.MINUS -> result.text = (first - second).toString()
            Operation.DIVIDE -> result.text = (first.toDouble() / second).toString()
            Operation.MULTIPLY -> result.text = (first * second).toString()
        }
    }

    private fun showSnackbar(message: String) {
        val snackbar = Snackbar.make(rootLayout, message, Snackbar.LENGTH_LONG)
        snackbar.view.apply {
            val changeLayoutParams = layoutParams as CoordinatorLayout.LayoutParams
            (this.findViewById(com.google.android.material.R.id.snackbar_text) as TextView).isSingleLine = false
            changeLayoutParams.gravity = Gravity.END or Gravity.TOP
            changeLayoutParams.marginEnd = resources.getDimension(R.dimen.marginBig).toInt()
            changeLayoutParams.bottomMargin = resources.getDimension(R.dimen.marginBig).toInt()
            this.layoutParams = changeLayoutParams
        }
        if (message.isNotBlank()) snackbar.show()
    }
}
