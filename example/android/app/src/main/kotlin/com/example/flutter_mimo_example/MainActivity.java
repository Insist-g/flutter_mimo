package com.example.flutter_mimo_example;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.miui.zeus.mimo.sdk.BannerAd;
import com.miui.zeus.mimo.sdk.MimoSdk;

import io.flutter.embedding.android.FlutterActivity;

public class MainActivity extends FlutterActivity{

}
//
//public class MainActivity extends Activity {
//    private static final String BANNER_POS_ID = "85d42885d3491aeb219fc2eff234f849";
//    private static final String TAG = "MIMO";
//
//    private BannerAd mBannerAd;
//
//    private ViewGroup mContainer;
//    private Button mFetchBtn;
//    private Button mShowBtn;
//
//    private BannerAd.BannerInteractionListener mBannerInteractionListener = new BannerAd.BannerInteractionListener() {
//        @Override
//        public void onAdClick() {
//            Log.d(TAG, "onAdClick");
//        }
//
//        @Override
//        public void onAdShow() {
//            mShowBtn.setEnabled(false);
//            Log.d(TAG, "onAdShow");
//        }
//
//        @Override
//        public void onAdDismiss() {
//            Log.d(TAG, "onAdDismiss");
//        }
//
//        @Override
//        public void onRenderSuccess() {
//            Log.d(TAG, "onRenderSuccess");
//        }
//
//        @Override
//        public void onRenderFail(int code, String msg) {
//            Log.e(TAG, "onRenderFail errorCode " + code + " errorMsg " + msg);
//        }
//    };
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        initMimoSdk();
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_banner);
//        mContainer = findViewById(R.id.container);
//        mFetchBtn = findViewById(R.id.fetchAd);
//        mShowBtn = findViewById(R.id.showAd);
//
//
//        mFetchBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                fetchAd();
//            }
//        });
//
//        mShowBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showAd();
//            }
//        });
//    }
//
//    private void showAd() {
//        mBannerAd.showAd(this, mContainer, mBannerInteractionListener);
//    }
//
//    private void initMimoSdk() {
//        MimoSdk.init(this, new MimoSdk.InitCallback() {
//            @Override
//            public void success() {
//                Log.d(TAG, "MimoSdk init success");
//            }
//
//            @Override
//            public void fail(int code, String msg) {
//                Log.e(TAG, "MimoSdk init fail, code=" + code + ",msg=" + msg);
//            }
//        });
//        MimoSdk.setDebugOn(false);
//        MimoSdk.setStagingOn(false);
//    }
//
//    private void fetchAd() {
//        mShowBtn.setEnabled(false);
//        if (mBannerAd != null) {
//            mBannerAd.destroy();
//        }
//        mBannerAd = new BannerAd();
//        mBannerAd.loadAd(BANNER_POS_ID, new BannerAd.BannerLoadListener() {
//            @Override
//            public void onBannerAdLoadSuccess() {
//                mShowBtn.setEnabled(true);
//            }
//
//            @Override
//            public void onAdLoadFailed(int errorCode, String errorMsg) {
//                Log.e(TAG, "errorCode " + errorCode + " errorMsg " + errorMsg);
//            }
//        });
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        try {
//            mBannerAd.destroy();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}