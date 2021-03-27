package com.example.a7minuteworkout

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.Adapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_exercise.*
import kotlinx.android.synthetic.main.are_you_sure_dialog_box.*
import org.w3c.dom.Text
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var restTimer : CountDownTimer?=null
    private var restProgress = 0
    private var exerciseList : ArrayList<ExerciseModel>?=null
    private var currentExercisePosition = -1
    private var textToSpeech : TextToSpeech?=null
    private var mediaPlayer : MediaPlayer? = null
    private var exerciseStatusAdapter : ExerciseAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)
        setSupportActionBar(toolbar_exercise_activity)
        val actionBar = supportActionBar
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        toolbar_exercise_activity.setNavigationOnClickListener {
            launchDialogBox()
        }

        textToSpeech = TextToSpeech(this,this)
        exerciseList = Constants.defaultExerciseList()
        setUpExerciseStatusRV()
        setUpRestView()


    }

    private fun setUpExerciseStatusRV() {
        exerciseStatusAdapter = ExerciseAdapter(exerciseList!!,this)
        exercise_status_rv.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        exercise_status_rv.adapter = exerciseStatusAdapter
    }


    private fun setUpRestView(){
        setUpPlayer()
        rest_view_ll.visibility = View.VISIBLE
        exercise_view_ll.visibility = View.INVISIBLE
        exerciseList!![currentExercisePosition+1].setSelected(true)
        exerciseStatusAdapter!!.notifyDataSetChanged()
        if(restTimer!=null){
            restTimer!!.cancel()
            restProgress=0
        }
        upcoming_exercise_tv.text = exerciseList!![currentExercisePosition+1].getName()
        setRestProgressBar()
        exercise_name_tv.text = "Get ready for " + exerciseList!![currentExercisePosition+1].getName()
    }



    private fun setRestProgressBar(){
        exercise_activity_rest_progress_bar.progress = restProgress
        restTimer = object : CountDownTimer(1000,1000){
            override fun onTick(millisUntilFinished: Long) {
                restProgress +=1
                exercise_activity_rest_progress_bar.progress=1-restProgress
                exercise_activity_timer_et.text=(1-restProgress).toString()
            }

            override fun onFinish() {
                currentExercisePosition+=1
                setUpExerciseView()
            }
        }.start()
    }

    private fun setUpExerciseView(){
        rest_view_ll.visibility = View.INVISIBLE
        exercise_view_ll.visibility = View.VISIBLE
        exerciseStatusAdapter!!.notifyDataSetChanged()
        if(restTimer!=null){
            restTimer!!.cancel()
            restProgress=0
        }
        setExerciseProgressBar()
        exercise_iv.setImageResource(exerciseList!![currentExercisePosition].getImage())
        exercise_name_tv.text= exerciseList!![currentExercisePosition].getName()
        speakOut(exerciseList!![currentExercisePosition].getName())
    }


    private fun setExerciseProgressBar(){
        restTimer = object : CountDownTimer(3000,1000){
            override fun onTick(millisUntilFinished: Long) {
                restProgress+=1
                exercise_activity_exercise_progress_bar.progress=3-restProgress
                exercise_activity_exercise_timer_et.text=(3-restProgress).toString()
            }

            override fun onFinish() {
                exerciseList!![currentExercisePosition].setSelected(false)
                exerciseList!![currentExercisePosition].setCompleted(true)
                if(currentExercisePosition<2)
                    setUpRestView()
                else{
                    goToFinish()

                }
            }
        }.start()
    }

    private fun goToFinish() {
        finish()
        val intent = Intent(this,FinishActivity::class.java)
        startActivity(intent)
    }

    private fun launchDialogBox(){
        val dialogBox = Dialog(this)
        dialogBox.setContentView(R.layout.are_you_sure_dialog_box)
        dialogBox.custom_dialog_box_yes_btn.setOnClickListener{
            dialogBox.dismiss()
            finish()
        }
        dialogBox.custom_dialog_box_no_btn.setOnClickListener{
            dialogBox.dismiss()
        }
        dialogBox.show()
    }
    override fun onInit(status: Int) {
        if(status == TextToSpeech.SUCCESS){
            var result = textToSpeech!!.setLanguage(Locale.US)
            if(result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA){
                Toast.makeText(this,"Text to speech unavailable", Toast.LENGTH_SHORT)
            }
        }
    }

    private fun setUpPlayer() {
        try {
            mediaPlayer = MediaPlayer.create(this,R.raw.press_start)
            mediaPlayer!!.isLooping
            mediaPlayer!!.start()
        }catch (e:Exception){
            e.printStackTrace()
        }
    }
    private fun speakOut(text:String){
        textToSpeech!!.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")


    }



    override fun onDestroy() {
        super.onDestroy()
        if (restTimer!=null){
            restTimer!!.cancel()
            restProgress=0
        }

        if (textToSpeech!=null){
            textToSpeech!!.stop()
            textToSpeech!!.shutdown()
        }

        if(mediaPlayer!=null){
            mediaPlayer!!.stop()
        }
    }



}