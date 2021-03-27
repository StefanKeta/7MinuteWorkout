package com.example.a7minuteworkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        start_button.setOnClickListener{
            val intent = Intent(this,ExerciseActivity::class.java)
            startActivity(intent)
        }

        activity_main_bmi.setOnClickListener{
            goToBMIActivity()
        }

        activity_main_history.setOnClickListener {
            goToHistoryActivity()
        }
    }

    private fun goToBMIActivity() {
        val intent = Intent(this,BMIActivity::class.java)
        startActivity(intent)
    }

    private fun goToHistoryActivity(){
        val intent = Intent(this, HistoryActivity::class.java)
        startActivity(intent)
    }


}