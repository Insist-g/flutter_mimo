package com.example.flutter_mimo

import android.content.Context
import android.view.View
import io.flutter.Log
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.platform.PlatformView
import java.util.Objects


/**
 * 提供 AndroidView 与 flutter 间的交互能力
 */
class CustomViewController(
    private val context: Context,
    messenger: BinaryMessenger,
    val id: Int,
    val params: HashMap<String, Any>
) : PlatformView, MethodChannel.MethodCallHandler {

    private var customView: CustomView? = null

    private val channel: MethodChannel = MethodChannel(
        messenger, "com.rex.custom.android/customView$id"
    )

    private var unRegisterLifecycleCallback: UnRegisterLifecycleCallback? = null

    init {
        // 如果需要在自定义view交互中申请监听权限可以加上下面这句话
        // CustomShared.binding?.addRequestPermissionsResultListener(this)

        channel.setMethodCallHandler(this)
        params.entries.forEach {
            Log.i("rex", "CustomView初始化接收入参：${it.key} - ${it.value}")
        }

        //监听 activity 生命周期
        unRegisterLifecycleCallback = CustomShared.activity?.registerLifecycleCallbacks(
            onPause = {
                // Activity 生命周期 onPause 在此响应
                Log.i("rex", "Activity 生命周期 onPause")
            },
            onResume = {
                // Activity 生命周期 onResume 在此响应
                Log.i("rex", "Activity 生命周期 onResume")
            },
            onDestroyed = {
                customView?.bannerDestroy()
            }
        )
    }

    override fun getView(): View = initCustomView()

    private fun initCustomView(): View {
        if (customView == null) {
            customView = CustomView(context, null)
            customView!!.setOnKeyEventCallback(object : OnKeyEventCallback {
                override fun onKeyEventCallback(message: String) {
                    // 将 Android 层的数据传递到 flutter 层
                    channel.invokeMethod(
                        "getMessageFromAndroidView",
                        "native - $message"
                    )
                }
            })
        }
        return customView!!
    }


    override fun dispose() {
        // flutterView dispose 生命周期 在此响应
        Log.i("rex", "flutterView on Dispose")
    }

    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        when (call.method) {
            "getMessageFromFlutterView" -> {
                customView?.getMessageFromFlutter(call.arguments.toString())
                result.success(true)
            }
            "initMimoSdk" -> {
                var isDebug = call.arguments as Boolean
                customView?.initMiMoSDK(isDebug, object : OnInitResult {
                    override fun success() {
                        result.success(true)
                    }

                    override fun error(v1: Int, v2: String) {
                        result.error(v1.toString(), v2, null)
                    }
                })
            }
            "loadBannerAd" -> {
                customView?.loadBannerAd()
                result.success(true)
            }
            "showBannerAd" -> {
                customView?.showBannerAd()
                result.success(true)
            }
            "loadInterstitialAd" -> {
                customView?.loadInterstitialAd()
                result.success(true)
            }
            "showInterstitialAd" -> {
                customView?.showInterstitialAd()
                result.success(true)
            }
            "dispose" -> {
                customView?.dispose()
                result.success(true)
            }
            else -> {
                result.notImplemented()
            }
        }
    }
}