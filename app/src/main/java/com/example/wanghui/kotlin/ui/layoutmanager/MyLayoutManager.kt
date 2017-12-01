package com.example.wanghui.kotlin.ui.layoutmanager

import android.support.v7.widget.OrientationHelper
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup

/**
 * Created by wanghui on 2017/9/25.
 */
class MyLayoutManager : RecyclerView.LayoutManager(){
    private val TAG = "MyLayoutManager"
    companion object {
        val DIRECTION_START = -1
        val DIRECTION_END = 1

        val HORIZONTAL = OrientationHelper.HORIZONTAL
        val VERTICAL = OrientationHelper.VERTICAL
    }
    private var orientationHelper = OrientationHelper.createVerticalHelper(this)
    private var orientation = VERTICAL
    private val layoutState = LayoutState()


    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT)
    }


    fun setOrientation(orientation : Int){
        if (orientation != HORIZONTAL && orientation != VERTICAL){
            return
        }
        this.orientation = orientation
        orientationHelper = when(orientation == HORIZONTAL){
            true -> OrientationHelper.createHorizontalHelper(this)
            false-> OrientationHelper.createVerticalHelper(this)
        }
    }

    override fun canScrollHorizontally(): Boolean = orientation == HORIZONTAL

    override fun canScrollVertically(): Boolean = orientation == VERTICAL

    override fun scrollHorizontallyBy(dx: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?): Int {
        if (orientation == VERTICAL) return 0
        return scrollBy(dx, recycler, state)
    }

    override fun scrollVerticallyBy(dy: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?): Int {
        if (orientation == HORIZONTAL) return 0
        return scrollBy(dy, recycler, state)
    }


    /**
     * 判断滚动边界、加载新条目、复用条目、回收条目
     */
    private fun scrollBy(dx: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?): Int {
        return 0
    }

    /**
     *
     */
    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        if(0==itemCount||state.isPreLayout){   //todo 这里的判断条件都是什么意思？
            detachAndScrapAttachedViews(recycler)
        } else if(state.didStructureChange()){  //todo 这个调用时机的重要性
            detachAndScrapAttachedViews(recycler)
            initLayoutState(recycler, state)
            fillViews(recycler, state)
        }
    }


    private fun initLayoutState(recycler: RecyclerView.Recycler, state: RecyclerView.State){
        layoutState.available = orientationHelper.totalSpace
    }

    /**
     * 填充视图 按方向布局view
     * 如果还有可用空间-》填充view
     */
    private fun fillViews(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        var available = layoutState.available
        while(available  > 0 && hasMore()){
            Log.d(TAG, "debug----$available")
            val consumed = layoutChild(recycler, state)
            available -= consumed
            layoutState.available = available
            layoutState.layoutOffset += consumed * layoutState.itemDirection
        }
    }

    private fun hasMore(): Boolean {
        return layoutState.position in 0 until itemCount
    }

    private fun layoutChild(recycler: RecyclerView.Recycler, state: RecyclerView.State) : Int{
        val view = nextView(recycler, state)
        addView(view)
        measureChildWithMargins(view, 0, 0)
        val consumed = orientationHelper.getDecoratedMeasurement(view)
        var left = paddingLeft
        var top = paddingTop
        var right: Int
        var bottom: Int
        if (orientation == VERTICAL){
            right = left + orientationHelper.getDecoratedMeasurementInOther(view)
            top = layoutState.layoutOffset
            bottom = top + consumed
        }else{
            bottom = top + orientationHelper.getDecoratedMeasurementInOther(view)
            left = layoutState.layoutOffset
            right = left + consumed
        }
        Log.d(TAG, "debug-----$left---$top---$right---$bottom")
        layoutDecorated(view, left, top, right, bottom)
        return consumed
    }

    private fun nextView(recycler: RecyclerView.Recycler, state: RecyclerView.State): View {
        val view = recycler.getViewForPosition(layoutState.position)
        layoutState.position ++
        return view
    }


    inner class LayoutState{
        /**
         * 当前有效空间,初始为控件本身大小,以后则为滑动到可以填充控件的位置
         */
        var available=0
        /**
         * 当前排版位置,排版
         */
        var layoutOffset =0
        /**
         * 当前位置
         */
        var position:Int=0
        /**
         * 当前操作条目方向
         */
        var itemDirection= DIRECTION_END
    }
}