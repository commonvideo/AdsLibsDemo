package com.common.util

import android.content.Context
import com.google.android.gms.ads.MobileAds

var idNativeBanner = "/6499/example/native"
var idNative = "/6499/example/native"
var idBanner = "/6499/example/banner"
var idFullScreen = "/6499/example/interstitial"
var idAppOpen = "/6499/example/app-open"


fun Context.initialize() {
    MobileAds.initialize(this)
}