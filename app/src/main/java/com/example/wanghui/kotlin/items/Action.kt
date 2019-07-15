package com.example.wanghui.kotlin.items

/**
 * Created by wanghui on 2019-05-28.
 */
interface Action {
    val type: String
    val data: Any
}

const val ACTION_ITEM_CLICK = "item_click"
const val ACTION_ITEM_EXPOSURE = "item_exposure"


class ItemClickAction(val url: String): Action{
    override val type: String
        get() = ACTION_ITEM_CLICK
    override val data: Any
        get() = url
}

class ItemExposureAction(val pos: String): Action{
    override val type: String
        get() = ACTION_ITEM_EXPOSURE
    override val data: Any
        get() = hashMapOf("itemSource" to "item", "pos" to pos)
}