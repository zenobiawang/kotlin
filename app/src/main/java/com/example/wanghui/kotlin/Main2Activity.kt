package com.example.wanghui.kotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.activity_test_mountingview.*
import org.jetbrains.anko.UI
import org.jetbrains.anko.dip
import org.jetbrains.anko.find
import org.jetbrains.anko.textView


class Main2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_mountingview)
        testMountingView()
//        setContentView(R.layout.activity_main2)
//        testScroller()
    }

    private fun testMountingView(){
        mountingHeader.layoutManager = LinearLayoutManager(this)
        mountingTail.layoutManager = LinearLayoutManager(this)
        mountingHeader.adapter = Adapter(createList("1"))
        mountingTail.adapter = Adapter(createList("2"))
    }
















    private fun testScroller() {
        recyclerView1.layoutManager = LinearLayoutManager(this)
        recyclerView2.layoutManager = LinearLayoutManager(this)
        recyclerView1.adapter = Adapter(createList("1"))
        recyclerView2.adapter = Adapter(createList("2"))
    }

    private fun createList(sub: String): List<String>{
        return (0..30).map {
            "$sub 测试 $it "
        }
    }

    class Adapter(val list: List<String>): RecyclerView.Adapter<MViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MViewHolder {
            return MViewHolder(parent!!)
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: MViewHolder?, position: Int) {
            holder?.onBindViewHolder(list.get(position))
        }

    }

    class MViewHolder(parent: ViewGroup): RecyclerView.ViewHolder(createView(parent)){
        companion object{
            fun createView(parent: ViewGroup) = parent.context.UI {
                textView {
                    id = R.id.text
                    layoutParams = ViewGroup.LayoutParams(dip(300),  dip(150))
                }
            }.view
        }

        fun onBindViewHolder(text: String){
            Log.d("wh", "wh------onbind $adapterPosition ${this.hashCode()}")
            itemView.find<TextView>(R.id.text).text = "HHHHHHHHH$text"
        }

    }
}
