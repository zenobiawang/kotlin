package com.example.wanghui.kotlin

import android.content.Context
import android.content.Intent
import android.content.res.TypedArray
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import com.example.wanghui.kotlin.test.TestAssetActivity
import com.example.wanghui.kotlin.ui.view.group.smsCode
import com.example.wanghui.kotlin.ui.view.line.TestLineViewActivity
import com.example.wanghui.kotlin.ui.view.roundview.TestRoundViewActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

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
        items.add(Item("roundView", TestRoundViewActivity::class.java))
    }

    /**
     * 列表adapter
     */
    inner class MyAdapter(var context: Context, var items : MutableList<Item>) : BaseAdapter(){
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
//            return with(context) {
//                linearLayout{
//                    orientation = LinearLayout.VERTICAL
//                    textView{
//                        text = items[position].itemName
//                        gravity = Gravity.CENTER
//                        onClick {
//                            val intent = Intent(this@MyAdapter.context, items[position].clazz)
//                            this@MyAdapter.context.startActivity(intent)
//                        }
//                    }.lparams(wrapContent, wrapContent)
//                    smsCode("hello")   //存疑
//                }
//            }
            return TextView(context).apply {
                layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15f, resources.displayMetrics).toInt()
                text = items[position].itemName
                onClick {
                    val intent = Intent(this@MyAdapter.context, items[position].clazz)
                    this@MyAdapter.context.startActivity(intent)
                }
            }
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