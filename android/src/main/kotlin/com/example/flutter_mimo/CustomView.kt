package com.example.flutter_mimo

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import com.example.flutter_mimo.CustomShared.activity
import com.miui.zeus.mimo.sdk.BannerAd
import com.miui.zeus.mimo.sdk.InterstitialAd
import com.miui.zeus.mimo.sdk.MimoSdk


/**
 *  android 渲染的自定义view 提供 flutter 使用
 */
class CustomView(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    private var TAG: String = "MIMO❤️"

    private var onKeyEventCallback: OnKeyEventCallback? = null
    private var viewGroup: ViewGroup? = null
    private var bannerAd: BannerAd? = null
    private var interstitialAd: InterstitialAd? = null

    init {
        val rootView = LayoutInflater.from(context).inflate(R.layout.layout_custom_view, this, true)
        initView(rootView)
    }

    fun bannerDestroy() {
        try {
            bannerAd?.destroy()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun initView(rootView: View) {
        viewGroup = rootView.findViewById(R.id.container)
    }

    fun setOnKeyEventCallback(callback: OnKeyEventCallback?) {
        onKeyEventCallback = callback
    }

    @SuppressLint("SetTextI18n")
    fun getMessageFromFlutter(message: String) {
        Log.d(TAG, "自来flutter的数据：$message")
    }

    @SuppressLint("SetTextI18n")
    fun initMiMoSDK(isDebug: Boolean, listener: OnInitResult) {
        Log.d(TAG, "initMiMoSDK")
        if (!MimoSdk.isInitSuccess()) {
            MimoSdk.init(context, object : MimoSdk.InitCallback {
                override fun success() {
                    Log.d(TAG, "1.1 MimoSdk init success")
                    listener.success()
                }

                override fun fail(code: Int, msg: String) {
                    listener.error(code, msg)
                    Log.d(TAG, "1.2 MimoSdk init fail, code=$code,msg=$msg")
                }
            })
            //以下为调试开关，上线需关闭，默认均为false
            MimoSdk.setDebugOn(isDebug)
            MimoSdk.setStagingOn(isDebug)
        } else {
            listener.success()
        }
    }

    @SuppressLint("SetTextI18n")
    fun loadBannerAd() {
        bannerAd?.destroy()
        bannerAd = null
        bannerAd = BannerAd()
        Log.d(TAG, "loadBannerAd")
        bannerAd?.loadAd("85d42885d3491aeb219fc2eff234f849", object : BannerAd.BannerLoadListener {
            override fun onBannerAdLoadSuccess() {
                Log.d(TAG, "2.1 MimoSdk loadAd success");
                showBannerAd()
            }

            override fun onAdLoadFailed(code: Int, msg: String?) {
                Log.d(TAG, "2.2 MimoSdk loadAd fail, code=$code,msg=$msg")
                onKeyEventCallback?.onKeyEventCallback(message = "LoadFailed")
            }

        })
    }

    @SuppressLint("SetTextI18n")
    fun showBannerAd() {
        Log.d(TAG, "showBannerAd")
        var ac = activity
        if (ac == null) {
            Log.d(TAG, "activity is null")
            return
        }
        Log.d(TAG, ac.localClassName)
        bannerAd?.showAd(ac,
            viewGroup,
            object : BannerAd.BannerInteractionListener {
                override fun onAdClick() {
                    Log.d(TAG, "3.1 MimoSdk onAdClick");
                }

                override fun onAdShow() {
                    Log.d(TAG, "3.2 MimoSdk onAdShow");
                }

                override fun onAdDismiss() {
                    Log.d(TAG, "3.3 MimoSdk onAdDismiss")
                    onKeyEventCallback?.onKeyEventCallback(message = "Dismiss")
                    bannerAd?.destroy()
                }

                override fun onRenderSuccess() {
                    Log.d(TAG, "3.4 MimoSdk onRenderSuccess");
                }

                override fun onRenderFail(code: Int, msg: String?) {
                    Log.d(TAG, "3.5 MimoSdk onRenderFail code=$code,msg=$msg")
                    onKeyEventCallback?.onKeyEventCallback(message = "LoadFailed")
                    bannerAd?.destroy()
                }

            })
    }

    @SuppressLint("SetTextI18n")
    fun loadInterstitialAd() {
        interstitialAd?.destroy()
        interstitialAd = null
        interstitialAd = InterstitialAd()
        Log.d(TAG, "loadInterstitialAd")
        interstitialAd?.loadAd(
            "37528997b09612db6911f52bbd7ca62d",
            object : InterstitialAd.InterstitialAdLoadListener {
                override fun onAdRequestSuccess() {
                    //广告请求成功
                    Log.d(TAG, "6.1 MimoSdk onAdRequestSuccess")
                }

                override fun onAdLoadSuccess() {
                    //广告加载（缓存）成功，在需要的时候在此处展示广告
                    Log.d(TAG, "6.2 MimoSdk onAdLoadSuccess")
                    showInterstitialAd()
                }

                override fun onAdLoadFailed(code: Int, msg: String?) {
                    Log.d(TAG, "6.3 MimoSdk loadInterstitialAd fail, code=$code,msg=$msg")
                    interstitialAd?.destroy()
                }

            })
    }

    @SuppressLint("SetTextI18n")
    fun showInterstitialAd() {
        Log.d(TAG, "showInterstitialAd")
        var ac = activity
        if (ac == null) {
            Log.d(TAG, "activity is null")
            return
        }
        Log.d(TAG, ac.localClassName)
        interstitialAd?.show(ac,
            object : InterstitialAd.InterstitialAdInteractionListener {
                override fun onAdClick() {
                    // 广告被点击
                }

                override fun onAdShow() {
                    // 广告展示
                }

                override fun onAdClosed() {
                    // 广告关闭
                    interstitialAd?.destroy()
                }

                override fun onRenderFail(code: Int, msg: String?) {
                    // 广告渲染失败
                    interstitialAd?.destroy()
                }

                override fun onVideoStart() {
                    //视频开始播放
                }

                override fun onVideoPause() {
                    //视频暂停
                }

                override fun onVideoResume() {
                    //视频继续播放;
                }

                override fun onVideoEnd() {
                    //视频播放结束;
                }
            })
    }

    @SuppressLint("SetTextI18n")
    fun dispose() {
        Log.d(TAG, "dispose")
        interstitialAd?.destroy()
        bannerAd?.destroy()
    }


}

interface OnKeyEventCallback {
    fun onKeyEventCallback(message: String)
}

interface OnInitResult {
    fun success()
    fun error(v1: Int, v2: String)
}