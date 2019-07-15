package com.example.wanghui.kotlin.items

/**
 * Created by wanghui on 2019-05-28.
 */
/**
 * 处理事件(点击事件响应和曝光事件)，返回值，是否自己处理点击事件
 */
typealias Executor = (Action) -> Boolean
