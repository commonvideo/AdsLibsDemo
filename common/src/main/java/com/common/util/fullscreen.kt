package com.common.util

import android.app.Activity
import android.content.Context
import android.util.Log
import com.facebook.ads.Ad
import com.facebook.ads.InterstitialAdListener
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

private var mInterstitialAd: InterstitialAd? = null
private var interstitialAdFacebook: com.facebook.ads.InterstitialAd? = null

fun Context.request(placement: String, listener: FullCallBack) {
    InterstitialAd.load(
        this,
        placement,
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


fun Context.requestFacebook(placement: String, listener: FullCallBack) {
    interstitialAdFacebook = com.facebook.ads.InterstitialAd(this, placement)
    interstitialAdFacebook?.loadAd(
        interstitialAdFacebook?.buildLoadAdConfig()
            ?.withAdListener(object : InterstitialAdListener {
                override fun onError(p0: Ad?, p1: com.facebook.ads.AdError?) {
                    listener.onError()
                }

                override fun onAdLoaded(p0: Ad?) {
                }

                override fun onAdClicked(p0: Ad?) {
                }

                override fun onLoggingImpression(p0: Ad?) {
                }

                override fun onInterstitialDisplayed(p0: Ad?) {
                }

                override fun onInterstitialDismissed(p0: Ad?) {
                }
            })
            ?.build()
    )
}

fun isLoadedFacebook(): Boolean {
    return interstitialAdFacebook != null && interstitialAdFacebook?.isAdLoaded == true
}


fun showFacebook() {
    interstitialAdFacebook?.show()
}
