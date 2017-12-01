package com.example.wanghui.kotlin.ui.layoutmanager

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.wanghui.kotlin.R

/**
 * Created by wanghui on 2017/11/7.
 */
class LayoutManagerAdapter(layoutManager1Activity: Context, map: List<String>) : RecyclerView.Adapter<LayoutManagerAdapter.ViewHolder>() {
    private val TAG = "layoutManagerAdapter"
    val context = layoutManager1Activity
    private val date = map

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        Log.d(TAG, "debug------onCreateViewHolder")
        val view = TextView(context).apply {
            background = context.getDrawable(R.color.little_yellow)
            layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d(TAG, "debug------onBindViewHolder")
        holder.textView.text = date[position]
    }


    override fun getItemCount(): Int {
        return date.size
    }

    class ViewHolder(view : TextView) : RecyclerView.ViewHolder(view) {
        val textView = view
    }
}