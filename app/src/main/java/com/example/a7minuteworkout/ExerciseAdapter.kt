package com.example.a7minuteworkout

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_exercise.view.*

class ExerciseAdapter(val exercises : ArrayList<ExerciseModel>,val context : Context) : RecyclerView.Adapter<ExerciseAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_exercise,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var model = exercises[position]
        holder.item.text = model.getID().toString()
        if (model.isSelected()){
            holder.item.background = ContextCompat.getDrawable(context,R.drawable.item_circular_thin_border_color_accent)
            holder.item.setTextColor(ContextCompat.getColor(context,R.color.colorAccent))
        }else if(model.isCompleted()){
            holder.item.background = ContextCompat.getDrawable(context,R.drawable.item_circular_color_accent_background)
            holder.item.setTextColor(Color.WHITE)
        }else{
            holder.item.background = ContextCompat.getDrawable(context,R.drawable.item_circular_color_grey_background)
            holder.item.setTextColor(Color.BLACK)
        }
    }

    override fun getItemCount(): Int {
        return exercises.size
    }
    class ViewHolder(val view:View) : RecyclerView.ViewHolder(view){
        val item = view.item_exercise_rv

    }


}