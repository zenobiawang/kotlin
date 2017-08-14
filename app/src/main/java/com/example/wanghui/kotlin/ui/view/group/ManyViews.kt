package com.example.wanghui.kotlin.ui.view.group

import android.widget.EditText
import android.widget.LinearLayout
import org.jetbrains.anko.*

/**
 * Created by wanghui on 2017/8/14.
 */
fun _LinearLayout.smsCode(name: String): EditText {
    var a : EditText? = null
    linearLayout {
        orientation = LinearLayout.HORIZONTAL

        textView("$name:") {
            textSize = 18f
        }.lparams(width = dip(50)
                ,height = dip(50)) {
        }

        a = editText().lparams (width = dip(50)
                ,height = dip(50)){
        }
    }
    return a!!
}