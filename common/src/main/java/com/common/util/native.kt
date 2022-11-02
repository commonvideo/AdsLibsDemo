package com.common.util

import android.app.Activity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RatingBar
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import com.google.android.gms.ads.*
import com.google.android.gms.ads.VideoController.VideoLifecycleCallbacks
import com.google.android.gms.ads.nativead.MediaView
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView

fun Activity.requestNative(
    placement: String, btnColor: Int,
    btnTxtColor: Int, layout: LinearLayout, listener: NativeCallBack
) {
    AdLoader.Builder(
        this,
        if (BuildConfig.DEBUG) idNative else placement
    )
        .forNativeAd { nativeAd ->
            val adView = layoutInflater
                .inflate(R.layout.ad_unified_large, null) as NativeAdView

            val btn = adView.findViewById<AppCompatButton>(R.id.ad_call_to_action)
            btn.setBackgroundResource(btnColor)
            btn.setTextColor(btnTxtColor)

            populateUnifiedNativeAdViewLarge(nativeAd, adView)
            layout.removeAllViews()
            layout.addView(adView)
            adView.bringToFront()
            layout.invalidate()
        }
        .withAdListener(object : AdListener() {
            override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                super.onAdFailedToLoad(loadAdError)
                listener.onError()
            }

            override fun onAdLoaded() {
                super.onAdLoaded()
                listener.onLoaded()
            }
        })
        .build()
        .loadAd(AdRequest.Builder().build())
}

private fun populateUnifiedNativeAdViewLarge(nativeAd: NativeAd, adView: NativeAdView) {
    val vc = nativeAd.mediaContent
    vc!!.videoController.videoLifecycleCallbacks = object : VideoLifecycleCallbacks() {
        override fun onVideoEnd() {
            super.onVideoEnd()
        }
    }
    val mediaView = adView.findViewById<MediaView>(R.id.ad_media)
    adView.mediaView = mediaView
    adView.headlineView = adView.findViewById(R.id.ad_headline)
    adView.bodyView = adView.findViewById(R.id.ad_body)
    adView.callToActionView = adView.findViewById(R.id.ad_call_to_action)
    adView.iconView = adView.findViewById(R.id.ad_app_icon)
    adView.priceView = adView.findViewById(R.id.ad_price)
    adView.starRatingView = adView.findViewById(R.id.ad_stars)
    adView.storeView = adView.findViewById(R.id.ad_store)
    adView.advertiserView = adView.findViewById(R.id.ad_advertiser)

    // Some assets are guaranteed to be in every UnifiedNativeAd.
    (adView.headlineView as AppCompatTextView?)!!.text = nativeAd.headline
    (adView.bodyView as AppCompatTextView?)!!.text = nativeAd.body
    (adView.callToActionView as AppCompatButton?)!!.text = nativeAd.callToAction

    // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
    // check before trying to display them.
    if (nativeAd.icon == null) {
        adView.iconView!!.visibility = View.GONE
    } else {
        (adView.iconView as ImageView?)!!.setImageDrawable(
            nativeAd.icon!!.drawable
        )
        adView.iconView!!.visibility = View.VISIBLE
    }
    if (nativeAd.price == null) {
        adView.priceView!!.visibility = View.GONE
    } else {
        adView.priceView!!.visibility = View.VISIBLE
        (adView.priceView as AppCompatTextView?)!!.text = nativeAd.price
    }
    if (nativeAd.store == null) {
        adView.storeView!!.visibility = View.GONE
    } else {
        adView.storeView!!.visibility = View.VISIBLE
        (adView.storeView as AppCompatTextView?)!!.text = nativeAd.store
    }
    if (nativeAd.starRating == null) {
        adView.starRatingView!!.visibility = View.GONE
    } else {
        (adView.starRatingView as RatingBar?)
            ?.setRating(nativeAd.starRating!!.toFloat())
        adView.starRatingView!!.visibility = View.VISIBLE
    }
    if (nativeAd.advertiser == null) {
        adView.advertiserView!!.visibility = View.GONE
    } else {
        (adView.advertiserView as AppCompatTextView?)!!.text = nativeAd.advertiser
        adView.advertiserView!!.visibility = View.VISIBLE
    }
    adView.setNativeAd(nativeAd)
}
