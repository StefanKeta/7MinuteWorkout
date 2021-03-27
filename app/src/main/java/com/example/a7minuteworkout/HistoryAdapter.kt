package com.example.a7minuteworkout

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_history.view.*
import kotlinx.android.synthetic.main.item_history_date.view.*

class HistoryAdapter(val context: Context, val items : ArrayList<String>): RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder(LayoutInflater.from(context).inflate(R.layout.item_history_date,parent,false))
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        var model = items[position]
        holder.itemDate.text = model
        holder.itemPosition.text = (position+1).toString()

        if(position %2 == 0){
            holder.itemView.setBackgroundColor(Color.parseColor("#EBEBEB"))
        }else{
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"))
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class HistoryViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val itemView = view.item_history_ll
        val itemDate = view.item_history_date_item_tv
        val itemPosition = view.item_history_date_position_tv

    }
}