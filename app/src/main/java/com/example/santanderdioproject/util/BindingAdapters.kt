package com.example.santanderdioproject.util

import android.graphics.Color
import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.DateFormat

@BindingAdapter("setPriority")
fun setPriority(view: TextView, priority: Int){
    when(priority){
        0 -> {
            view.text = "High"
            view.setTextColor(Color.RED)
        }
        1 -> {
            view.text = "Medium"
            view.setTextColor(Color.BLUE)
        }
        2 -> {
            view.text = "Low"
            view.setTextColor(Color.GREEN)
        }
    }
}

@BindingAdapter("setTimestamp")
fun setTimestamp(view: TextView, timestamp: Long){
    view.text = DateFormat.getInstance().format(timestamp)
}