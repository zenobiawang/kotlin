package com.example.wanghui.kotlin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.wanghui.kotlin.test.TestAssetActivity
import com.example.wanghui.kotlin.ui.view.line.TestLineViewActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by wanghui on 2017/8/10.
 * 主页
 */
class MainActivity : AppCompatActivity(){
    lateinit var items : MutableList<Item>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initItems()
        mainListView.adapter = MyAdapter(this, items)
    }

    private fun initItems() {
        items = ArrayList()
        items.add(Item("line", TestLineViewActivity::class.java))
        items.add(Item("test", TestAssetActivity::class.java))
    }

    inner class MyAdapter(var context: Context, var items : MutableList<Item>) : BaseAdapter(){
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val textView : TextView = TextView(context)
            textView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            textView.text = items[position].itemName
            textView.setOnClickListener {
                run {
                    val intent = Intent(context, items[position].clazz)
                    context.startActivity(intent)
                }
            }

            return textView
        }

        override fun getItem(position: Int): Any {
            return items[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return items.size
        }

    }


    class Item(var itemName: String, var clazz: Class<*>)
}