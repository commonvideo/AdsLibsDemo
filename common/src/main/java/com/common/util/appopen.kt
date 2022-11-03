package com.common.util

import android.app.Activity
import android.content.Context
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd
import java.util.*

private var appOpenAd: AppOpenAd? = null
private var loadTime: Long = 0

private fun wasLoadTimeLessThanNHoursAgo(): Boolean {
    val dateDifference = Date().time - loadTime
    val numMilliSecondsPerHour: Long = 3600000
    return dateDifference < numMilliSecondsPerHour * 4
}

fun Context.requestAppOpen(placement: String, listener: AppOpenCallBack) {
    AppOpenAd.load(
        this,
        placement,
        AdRequest.Builder().build(),
        AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT,
        object : AppOpenAd.AppOpenAdLoadCallback() {
            override fun onAdLoaded(ad: AppOpenAd) {
                super.onAdLoaded(ad)
                appOpenAd = ad
                loadTime = Date().time
                listener.onLoaded()
            }

            override fun onAdFailedToLoad(p0: LoadAdError) {
                super.onAdFailedToLoad(p0)
                listener.onError()
            }
        }
    )
}

fun isLoadedAppOpen(): Boolean {
    return appOpenAd != null && wasLoadTimeLessThanNHoursAgo()
}

fun Activity.showAppOpen(listener: CallBack) {
    appOpenAd?.show(this)
    appOpenAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
        override fun onAdDismissedFullScreenContent() {
            appOpenAd = null
            listener.onCompleted()
        }

        override fun onAdFailedToShowFullScreenContent(adError: AdError) {
            listener.onCompleted()
        }

        override fun onAdShowedFullScreenContent() {

        }
    }
}