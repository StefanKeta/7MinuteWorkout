package com.example.a7minuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import kotlinx.android.synthetic.main.activity_bmi.*
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity(),View.OnClickListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi)

        setSupportActionBar(activity_bmi_toolbar)
        val actionBar = supportActionBar
        if(actionBar!=null){
            configureActionBar(actionBar)
        }

        activity_bmi_calculate_units_btn.setOnClickListener(this)
        activity_bmi_metric_units_radio_button.setOnClickListener(this)
        activity_bmi_us_units_radio_button.setOnClickListener(this)
    }

    private fun configureActionBar(actionBar:ActionBar) {
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.title = "BMI CALCULATOR"
        activity_bmi_toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun validateMetricUnits():Boolean{
        if(activity_bmi_metric_units_radio_button.isChecked){
            if(activity_bmi_metric_unit_height_et.text.toString().isEmpty() || activity_bmi_metric_unit_weight_et.text.toString().isEmpty()){
                return false
            }
        }
        else if(activity_bmi_us_units_radio_button.isChecked){
            if(activity_bmi_us_unit_weight_et.text.toString().isEmpty() || activity_bmi_us_unit_feet_et.text.toString().isEmpty()||
                    activity_bmi_us_unit_inches_et.text.toString().isEmpty()){
                return false
            }
        }

        return true
    }

    private fun calculateBMI(){
        if(validateMetricUnits()){
            var bmi:Float?=null
            if (activity_bmi_metric_units_radio_button.isChecked){
                val height = activity_bmi_metric_unit_height_et.text.toString().toFloat() / 100
                val weight = activity_bmi_metric_unit_weight_et.text.toString().toFloat()
                bmi = calculateWithMetrics(height,weight)
            }else if(activity_bmi_us_units_radio_button.isChecked){
                val weight = activity_bmi_us_unit_weight_et.text.toString().toFloat()
                val feet = activity_bmi_us_unit_feet_et.text.toString().toFloat()
                val inches = activity_bmi_us_unit_inches_et.text.toString().toFloat()
                bmi = calculateWithUSUnits(feet,inches,weight)
            }

            displayBMI(bmi!!)
        }else{
            Toast.makeText(this,"Please enter valid metrics",Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun calculateWithMetrics(height:Float,weight:Float):Float{
        return weight/(height*height)
    }

    private fun calculateWithUSUnits(feet:Float,inches:Float,weight: Float):Float{
        val totalInches = feet*12+inches
        return (weight/(totalInches*totalInches))*703
    }

    private fun displayBMI(bmi:Float){
        val bmiLabel : String
        val bmiDescription:String
        if(bmi.compareTo(15f)<=0){
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! You really need to take care of yourself better! Eat more!"
        }

        else if (bmi.compareTo(15f) > 0 && java.lang.Float.compare(
                bmi,
                16f
            ) <= 0
        ) {
            bmiLabel = "Severely underweight"
            bmiDescription = "Oops! You really need to take care of your better! Eat more!"
        } else if (java.lang.Float.compare(bmi, 16f) > 0 && java.lang.Float.compare(
                bmi,
                18.5f
            ) <= 0
        ) {
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take care of your better! Eat more!"
        } else if (java.lang.Float.compare(bmi, 18.5f) > 0 && java.lang.Float.compare(
                bmi,
                25f
            ) <= 0
        ) {
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a good shape!"
        } else if (java.lang.Float.compare(bmi, 25f) > 0 && java.lang.Float.compare(
                bmi,
                30f
            ) <= 0
        ) {
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (java.lang.Float.compare(bmi, 30f) > 0 && java.lang.Float.compare(
                bmi,
                35f
            ) <= 0
        ) {
            bmiLabel = "Obese Class | (Moderately obese)"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (java.lang.Float.compare(bmi, 35f) > 0 && java.lang.Float.compare(
                bmi,
                40f
            ) <= 0
        ) {
            bmiLabel = "Obese Class || (Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        } else {
            bmiLabel = "Obese Class ||| (Very Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }

        activity_bmi_description_tv.visibility = View.VISIBLE
        activity_bmi_type_tv.visibility = View.VISIBLE
        activity_bmi_your_bmi_value_tv.visibility= View.VISIBLE
        activity_bmi_your_bmi_tv.visibility = View.VISIBLE

        val bmiValue =  BigDecimal(bmi.toDouble()).setScale(2,RoundingMode.HALF_EVEN).toString()
        activity_bmi_your_bmi_value_tv.text= bmiValue
        activity_bmi_type_tv.text = bmiLabel
        activity_bmi_description_tv.text = bmiDescription
    }

    private fun setUSUnits(){
        activity_bmi_metric_units_radio_button.isChecked = false
        activity_bmi_metric_unit_weight.visibility = View.GONE
        activity_bmi_metric_unit_height.visibility=View.GONE
        activity_bmi_us_units_radio_button.isChecked = true
        activity_bmi_us_unit_weight.visibility = View.VISIBLE
        activity_bmi_us_unit_feet.visibility=View.VISIBLE
        activity_bmi_us_unit_inches.visibility = View.VISIBLE

        activity_bmi_description_tv.visibility = View.INVISIBLE
        activity_bmi_type_tv.visibility = View.INVISIBLE
        activity_bmi_your_bmi_value_tv.visibility= View.INVISIBLE
        activity_bmi_your_bmi_tv.visibility = View.INVISIBLE

        activity_bmi_us_unit_feet_et.setText("")
        activity_bmi_us_unit_weight_et.setText("")
        activity_bmi_us_unit_inches_et.setText("")

    }

    private fun setMetricUnits(){
        activity_bmi_metric_units_radio_button.isChecked = true
        activity_bmi_metric_unit_weight.visibility = View.VISIBLE
        activity_bmi_metric_unit_height.visibility=View.VISIBLE
        activity_bmi_us_units_radio_button.isChecked = false
        activity_bmi_us_unit_weight.visibility = View.GONE
        activity_bmi_us_unit_feet.visibility=View.GONE
        activity_bmi_us_unit_inches.visibility = View.GONE

        activity_bmi_description_tv.visibility = View.INVISIBLE
        activity_bmi_type_tv.visibility = View.INVISIBLE
        activity_bmi_your_bmi_value_tv.visibility= View.INVISIBLE
        activity_bmi_your_bmi_tv.visibility = View.INVISIBLE

        activity_bmi_metric_unit_weight_et.setText("")
        activity_bmi_metric_unit_height_et.setText("")
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            activity_bmi_calculate_units_btn.id -> calculateBMI()
            activity_bmi_us_units_radio_button.id -> setUSUnits()
            activity_bmi_metric_units_radio_button.id -> setMetricUnits()
        }
    }
}