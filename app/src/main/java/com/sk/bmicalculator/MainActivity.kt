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

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

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
                val WeightCard = findViewById<CardView>(R.id.cvWeight)
                val HeightCard = findViewById<CardView>(R.id.cvHeight)
                val ResultCard  = findViewById<CardView>(R.id.cvResult)

                calculate.visibility = VISIBLE
                clear.visibility = INVISIBLE
                WeightCard.visibility = VISIBLE
                HeightCard.visibility = VISIBLE
                ResultCard.visibility = INVISIBLE

                weightText.text.clear()
                heightText.text.clear()
            }

        }

    }
    private fun validateInput(Weight:String?,Height:String?):Boolean {

        when{
            Weight.isNullOrEmpty() -> {
                Toast.makeText(this,"Enter Your Weight Nigga!",Toast.LENGTH_SHORT).show()
                return false
            }
            Height.isNullOrEmpty() ->{
                Toast.makeText(this,"Enter Your Height Mf#@!",Toast.LENGTH_SHORT).show()
                return false
            }
            else -> return true
        }
    }

    private fun calculateInput(bmi:Float)
    {
        val WeightCard = findViewById<CardView>(R.id.cvWeight)
        val HeightCard = findViewById<CardView>(R.id.cvHeight)
        val ResultCard  = findViewById<CardView>(R.id.cvResult)
        val index = findViewById<TextView>(R.id.tvIndex)
        val result = findViewById<TextView>(R.id.tvResult)
        val info  = findViewById<TextView>(R.id.tvInfo)

        info.setText("(Normal Range is 18.5 - 24.9)")
        WeightCard.visibility = INVISIBLE
        HeightCard.visibility = INVISIBLE
        ResultCard.visibility = VISIBLE

        index.setText(bmi.toString())

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
        result.setText(resultText)
    }

}