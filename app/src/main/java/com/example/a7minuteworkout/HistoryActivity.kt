package com.example.a7minuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_history.*

class HistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        configureActionBar()
        getAllCompletedDatesFromDatabase()

    }

    private fun configureActionBar(){
        setSupportActionBar(activity_history_toolbar)
        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        activity_history_toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun getAllCompletedDatesFromDatabase(){
        val list = ArrayList<String>()
        val db = SqlLiteOpenHelper(this,null)
        list.addAll( db.getAllCompleteDates())
        for(i in list){
            Log.d("List Item", "getAllCompletedDatesFromDatabase: "+i)
        }
        populateRecyclerView(list)
    }

    private fun populateRecyclerView(list:ArrayList<String>){
        if(!list.isEmpty()){
            activity_history_rv.visibility = View.VISIBLE
            activity_history_no_data_tv.visibility=View.GONE
            val adapter = HistoryAdapter(this,list)
            activity_history_rv.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
            activity_history_rv.adapter=adapter
            activity_history_rv.hasFixedSize()
        }else{
            activity_history_rv.visibility = View.GONE
            activity_history_no_data_tv.visibility=View.VISIBLE
        }
    }
}