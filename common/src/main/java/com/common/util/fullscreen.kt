package com.common.util

import android.app.Activity
import android.content.Context
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

private var mInterstitialAd: InterstitialAd? = null

fun Context.request(placement: String, listener: FullCallBack) {
    InterstitialAd.load(
        this,
        if (BuildConfig.DEBUG) idFullScreen else placement,
        AdRequest.Builder().build(),
        object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                mInterstitialAd = null
                listener.onError()
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                mInterstitialAd = interstitialAd
            }
        })
}

fun isLoaded(): Boolean {
    return mInterstitialAd != null
}

fun Activity.show(listener: CallBack) {
    mInterstitialAd?.show(this)
    mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {

        override fun onAdDismissedFullScreenContent() {
            mInterstitialAd = null
            listener.onCompleted()
        }

        override fun onAdFailedToShowFullScreenContent(p0: AdError) {
            super.onAdFailedToShowFullScreenContent(p0)
            mInterstitialAd = null
            listener.onCompleted()
        }
    }
}