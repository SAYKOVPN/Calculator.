package com.example.calculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    private lateinit var display: TextView

    private var firstNumber = 0.0
    private var operator = ""
    private var newNumber = true

    private val formatter = DecimalFormat("0.##########")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        display = findViewById(R.id.display)

        setupButtons()
    }

    private fun setupButtons() {

        val numbers = listOf(
            R.id.button0,
            R.id.button1,
            R.id.button2,
            R.id.button3,
            R.id.button4,
            R.id.button5,
            R.id.button6,
            R.id.button7,
            R.id.button8,
            R.id.button9
        )

        numbers.forEach { id ->
            findViewById<Button>(id).setOnClickListener {
                enterNumber((it as Button).text.toString())
            }
        }

        findViewById<Button>(R.id.buttonDot).setOnClickListener {
            enterDot()
        }

        findViewById<Button>(R.id.buttonPlus).setOnClickListener {
            chooseOperator("+")
        }

        findViewById<Button>(R.id.buttonMinus).setOnClickListener {
            chooseOperator("-")
        }

        findViewById<Button>(R.id.buttonMultiply).setOnClickListener {
            chooseOperator("×")
        }

        findViewById<Button>(R.id.buttonDivide).setOnClickListener {
            chooseOperator("÷")
        }

        findViewById<Button>(R.id.buttonEquals).setOnClickListener {
            calculate()
        }

        findViewById<Button>(R.id.buttonClear).setOnClickListener {
            clear()
        }

        findViewById<Button>(R.id.buttonPercent).setOnClickListener {
            percent()
        }

        findViewById<Button>(R.id.buttonPlusMinus).setOnClickListener {
            changeSign()
        }
    }

    private fun enterNumber(number: String) {
        if (newNumber || display.text.toString() == "0") {
            display.text = number
            newNumber = false
        } else {
            display.append(number)
        }
    }

    private fun enterDot() {
        if (newNumber) {
            display.text = "0."
            newNumber = false
        } else if (!display.text.contains(".")) {
            display.append(".")
        }
    }

    private fun chooseOperator(selectedOperator: String) {
        firstNumber = display.text.toString().toDoubleOrNull() ?: 0.0
        operator = selectedOperator
        newNumber = true
    }

    private fun calculate() {

        val secondNumber =
            display.text.toString().toDoubleOrNull() ?: return

        val result = when (operator) {

            "+" -> firstNumber + secondNumber

            "-" -> firstNumber - secondNumber

            "×" -> firstNumber * secondNumber

            "÷" -> {
                if (secondNumber == 0.0) {
                    display.text = "Ошибка"
                    newNumber = true
                    return
                }

                firstNumber / secondNumber
            }

            else -> secondNumber
        }

        display.text = formatter.format(result)

        operator = ""
        newNumber = true
    }

    private fun clear() {
        display.text = "0"
        firstNumber = 0.0
        operator = ""
        newNumber = true
    }

    private fun percent() {

        val number =
            display.text.toString().toDoubleOrNull() ?: return

        display.text = formatter.format(number / 100)

        newNumber = true
    }

    private fun changeSign() {

        val number =
            display.text.toString().toDoubleOrNull() ?: return

        display.text = formatter.format(-number)
    }
}