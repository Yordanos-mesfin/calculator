package com.example.calculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var tvDisplay: TextView
    private var firstOperand: Double = 0.0
    private var currentOperator: String? = null
    private var isNewOp = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tvDisplay = findViewById(R.id.tvDisplay)

        val buttons = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        )

        buttons.forEach { id ->
            findViewById<Button>(id).setOnClickListener {
                val button = it as Button
                onNumberClick(button.text.toString())
            }
        }

        findViewById<Button>(R.id.btnPlus).setOnClickListener { onOperatorClick("+") }
        findViewById<Button>(R.id.btnMinus).setOnClickListener { onOperatorClick("-") }
        findViewById<Button>(R.id.btnMultiply).setOnClickListener { onOperatorClick("*") }
        findViewById<Button>(R.id.btnDivide).setOnClickListener { onOperatorClick("/") }

        findViewById<Button>(R.id.btnEqual).setOnClickListener { onEqualClick() }
        findViewById<Button>(R.id.btnClear).setOnClickListener { onClearClick() }
    }

    private fun onNumberClick(number: String) {
        if (isNewOp) {
            tvDisplay.text = ""
        }
        isNewOp = false
        val currentText = tvDisplay.text.toString()
        tvDisplay.text = if (currentText == "0") number else currentText + number
    }

    private fun onOperatorClick(operator: String) {
        firstOperand = tvDisplay.text.toString().toDoubleOrNull() ?: 0.0
        currentOperator = operator
        isNewOp = true
    }

    private fun onEqualClick() {
        val secondOperand = tvDisplay.text.toString().toDoubleOrNull() ?: 0.0
        val result = when (currentOperator) {
            "+" -> firstOperand + secondOperand
            "-" -> firstOperand - secondOperand
            "*" -> firstOperand * secondOperand
            "/" -> if (secondOperand != 0.0) firstOperand / secondOperand else Double.NaN
            else -> secondOperand
        }
        tvDisplay.text = if (result % 1 == 0.0) result.toInt().toString() else result.toString()
        isNewOp = true
    }

    private fun onClearClick() {
        tvDisplay.text = "0"
        firstOperand = 0.0
        currentOperator = null
        isNewOp = true
    }
}
