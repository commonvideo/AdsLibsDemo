package com.common

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import com.common.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        int()
        val adsContainerLarge: LinearLayout = findViewById(R.id.adsContainerLarge)
        val banner: LinearLayout = findViewById(R.id.adsContainer)
        val adsContainerNative: LinearLayout = findViewById(R.id.adsContainerNative)
        val adsContainerFb: LinearLayout = findViewById(R.id.adsContainerFb)


        //todo full screen ads
        request(idFullScreen, object : FullCallBack {
            override fun onError() {

            }
        })

        val handler = Handler(Looper.getMainLooper())
        val runnable: Runnable = object : Runnable {
            override fun run() {
                if (isLoaded()) {
                    show(object : CallBack {
                        override fun onCompleted() {

                        }
                    })
                } else {
                    handler.postDelayed(this, 250)
                }
            }
        }
        handler.postDelayed(runnable, 250)

        //todo App open
        requestAppOpen(idAppOpen, object : AppOpenCallBack {
            override fun onLoaded() {
                if (isLoadedAppOpen()) {
                    showAppOpen(object : CallBack {
                        override fun onCompleted() {

                        }
                    })
                }
            }

            override fun onError() {

            }
        })

        //todo native
        requestNative(idNative,
            R.drawable.btn_ads,
            R.color.black,
            adsContainerLarge,
            object : NativeCallBack {
                override fun onLoaded() {
                    adsContainerLarge.visibility = View.VISIBLE
                }

                override fun onError() {

                }
            })


        //todo Banner
        requestBanner(
            idBanner,
            banner,
            object : BannerCallBack {
                override fun onLoaded() {
                    banner.visibility = View.VISIBLE
                }

                override fun onError() {

                }
            })

        //todo native banner
        requestNativeBanner(
            idNative,
            adsContainerNative,
            R.drawable.btn_ads,
            R.color.black,
            object : BannerCallBack {
                override fun onLoaded() {
                    adsContainerNative.visibility = View.VISIBLE
                }

                override fun onError() {

                }
            })

        requestFacebookBanner("YOUR_PLACEMENT_ID", adsContainerFb, object : BannerCallBack {
            override fun onLoaded() {
                adsContainerFb.visibility = View.VISIBLE
                Log.e("requestFacebookBanner", "requestFacebookBanner ---->> onLoaded")
            }

            override fun onError() {
                Log.e("requestFacebookBanner", "requestFacebookBanner ---->> onError")
            }
        })

        //todo native Facebook
        requestNativeFacebook("YOUR_PLACEMENT_ID",
            R.drawable.btn_ads,
            R.color.black,
            adsContainerLarge,
            object : NativeCallBack {
                override fun onLoaded() {
                    adsContainerLarge.visibility = View.VISIBLE
                }

                override fun onError() {

                }
            })


        //todo full screen ads facebook
        requestFacebook("YOUR_PLACEMENT_ID",
            object : FullCallBack {
                override fun onError() {
                    Log.e("requestFacebook", "requestFacebook ---->> onError")
                }
            })


        val handlerFb = Handler(Looper.getMainLooper())
        val runnableFb: Runnable = object : Runnable {
            override fun run() {
                if (isLoadedFacebook()) {
                    showFacebook()
                } else {
                    handlerFb.postDelayed(this, 250)
                }
            }
        }
        handlerFb.postDelayed(runnableFb, 250)
    }


}