package com.sk.bmicalculator

import android.os.Bundle
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import kotlin.text.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        Toast.makeText(this, "Made with Love: shahil-sk", Toast.LENGTH_LONG).show()

        val calculate  = findViewById<Button>(R.id.btnCalculate)
        val weightText = findViewById<EditText>(R.id.etWeight)
        val heightText = findViewById<EditText>(R.id.etHeight)
        val clear = findViewById<Button>(R.id.btnClear)

        calculate.setOnClickListener()
        {
            val weight = weightText.text.toString()
            val height = heightText.text.toString()

            if(validateInput(weight,height))
            {
                val bmi = weight.toFloat()/((height.toFloat()/100)*(height.toFloat()/100))
                //Converting BMI to 2 digit Decimal
                val bmi2Digit = String.format("%.2f",bmi).toFloat()
                calculate.visibility = INVISIBLE
                clear.visibility = VISIBLE
                calculateInput(bmi2Digit)
            }

            clear.setOnClickListener()
            {
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

        when{
            weight.isNullOrEmpty() -> {
                Toast.makeText(this,"Enter Your Weight Nigga!",Toast.LENGTH_SHORT).show()
                return false
            }

            height.isNullOrEmpty() -> {
                Toast.makeText(this,"Enter Your Height Mf#@!",Toast.LENGTH_SHORT).show()
                return false
            }
            else -> return true
        }
    }

    private fun calculateInput(bmi:Float)
    {
        val weightCard = findViewById<CardView>(R.id.cvWeight)
        val heightCard = findViewById<CardView>(R.id.cvHeight)
        val resultCard = findViewById<CardView>(R.id.cvResult)
        val indexText = findViewById<TextView>(R.id.tvIndex)
        val result = findViewById<TextView>(R.id.tvResult)
        val info  = findViewById<TextView>(R.id.tvInfo)

        weightCard.visibility = INVISIBLE
        heightCard.visibility = INVISIBLE
        resultCard.visibility = VISIBLE

        val index = bmi.toString()
        val msg = "(Normal Range is 18.5 - 24.9)"
        info.text = msg
        indexText.text = index

        var resultText = ""
        var color = 0

        when{
            bmi < 18.50 -> {
                resultText = "UnderWeight"
                color = R.color.underWeight
            }
            bmi in 18.50..24.9 ->
            {
                resultText = "Normal"
                color = R.color.normalWeight
            }
            bmi in 25.0..29.9 ->
            {
                resultText = "OverWeight"
                color = R.color.overWeight
            }
            bmi > 29.9 ->
            {
                resultText = "You Fat Nigga"
                color = R.color.obeseWeight
            }
        }

        result.setTextColor(ContextCompat.getColor(this,color))
        result.text = resultText
    }

}