package com.example.wanghui.kotlin.ui.scroll

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.example.wanghui.kotlin.R

/**
 * Created by wanghui on 2017/7/20.
 */
class PullToRefreshLinearLayout : PullToRefreshLayout<LinearLayout> {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun loadHeaderView(context: Context?): View {
        val headerView : TextView = TextView(context)
        headerView.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, 400)
        headerView.setBackgroundResource(R.color.colorPrimary)
        return headerView
    }

    override fun loadFooterView(context: Context?): View {
        val headerView : TextView = TextView(context)
        headerView.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, 400)
        headerView.setBackgroundResource(R.color.colorPrimary)
        return headerView
    }

    override fun loadContentView(context: Context?): LinearLayout {
        val contentView : LinearLayout = LinearLayout(context)
        contentView.setBackgroundResource(R.color.colorAccent)
        return contentView
    }
}
