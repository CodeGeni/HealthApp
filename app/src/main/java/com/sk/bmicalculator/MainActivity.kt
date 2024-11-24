package com.sk.bmicalculator

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import java.util.Locale
import kotlin.text.*

class MainActivity : AppCompatActivity() {
    lateinit var weightText: EditText
    lateinit var heightText: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        Toast.makeText(this, "Created by Felipe M Cotta", Toast.LENGTH_LONG).show()

        val calculate = findViewById<Button>(R.id.btnCalculate)
        weightText = findViewById(R.id.etWeight)
        heightText = findViewById(R.id.etHeight)
        val clear = findViewById<Button>(R.id.btnClear)
        val menu = findViewById<ImageButton>(R.id.about)
        val menu1 = findViewById<ImageButton>(R.id.about1)

        menu.setOnClickListener()
        {
            Log.i("info", "Redirect to About Page")
            startActivity(Intent(this, aboutPage::class.java))
        }

        menu1.setOnClickListener()
        {
            Log.i("info", "Redirect to About Page")
            startActivity(Intent(this, aboutPage::class.java))
        }

        calculate.setOnClickListener()
        {
            val weight = weightText.text.toString()
            val height = heightText.text.toString()

            if (validateInput(weight, height)) {
                val bmi = weight.toFloat() / ((height.toFloat() / 100) * (height.toFloat() / 100))

                val bmi2Digit = String.format(Locale.US, "%.2f", bmi).toFloat()

                Log.i("info", "BMI is : $bmi2Digit")
                calculate.visibility = INVISIBLE
                clear.visibility = VISIBLE
                calculateInput(bmi2Digit)
            }


            clear.setOnClickListener()
            {
                Log.i("info", "Cleared Data!!")
                val weightCard = findViewById<CardView>(R.id.cvWeight)
                val heightCard = findViewById<CardView>(R.id.cvHeight)
                val resultCard = findViewById<CardView>(R.id.cvResult)

                calculate.visibility = VISIBLE
                clear.visibility = INVISIBLE
                weightCard.visibility = VISIBLE
                heightCard.visibility = VISIBLE
                resultCard.visibility = INVISIBLE

                weightText.text.clear()
                heightText.text.clear()
            }

        }

    }

    private fun validateInput(weight: String?, height: String?): Boolean {

        when {
            weight.isNullOrEmpty() -> {
                Toast.makeText(this, "Enter Your Weight Nigga!", Toast.LENGTH_SHORT).show()
                Log.i("info", "InputArea (Weight) Empty !!")
                return false
            }

            height.isNullOrEmpty() -> {
                Toast.makeText(this, "Enter Your Height Nigga!", Toast.LENGTH_SHORT).show()
                Log.i("info", "InputArea (Height) Empty !!")
                return false
            }

            else -> return true
        }
    }

    private fun calculateInput(bmi: Float) {
        val weightCard = findViewById<CardView>(R.id.cvWeight)
        val heightCard = findViewById<CardView>(R.id.cvHeight)
        val resultCard = findViewById<CardView>(R.id.cvResult)
        val indexText = findViewById<TextView>(R.id.tvIndex)
        val result = findViewById<TextView>(R.id.tvResult)
        val info = findViewById<TextView>(R.id.tvInfo)

        weightCard.visibility = INVISIBLE
        heightCard.visibility = INVISIBLE
        resultCard.visibility = VISIBLE

        val index = bmi.toString()
        val msg = "(Normal Range is 18.5 - 24.9)"
        Log.i("info", msg)
        info.text = msg
        indexText.text = index

        var resultText = ""
        var color = 0

        when {
            bmi < 18.50 -> {
                resultText = "UnderWeight"
                color = R.color.underWeight
            }

            bmi in 18.50..24.9 -> {
                resultText = "Normal"
                color = R.color.normalWeight
            }

            bmi in 25.0..29.9 -> {
                resultText = "OverWeight"
                color = R.color.overWeight
            }

            bmi > 29.9 -> {
                resultText = "Obesity"
                color = R.color.obeseWeight
            }
        }

        result.setTextColor(ContextCompat.getColor(this, color))
        result.text = resultText
    }

    override fun onDestroy() {
        super.onDestroy()
        weightText.text.clear()
        heightText.text.clear()
    }
}