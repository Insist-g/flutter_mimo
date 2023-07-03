package com.example.flutter_mimo

import android.annotation.SuppressLint
import android.app.Activity
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding

@SuppressLint("StaticFieldLeak")
/**
 * plugin 与 Activity 绑定后，保存activity变量提供给 customView 使用
 */
object CustomShared {

    var activity: Activity? = null

    var binding: ActivityPluginBinding? = null

}