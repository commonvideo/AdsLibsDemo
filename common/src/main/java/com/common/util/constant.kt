package com.common.util

import android.content.Context
import com.facebook.ads.AdSettings
import com.facebook.ads.AudienceNetworkAds
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import java.util.*

var idNative = "/6499/example/native"
var idBanner = "/6499/example/banner"
var idFullScreen = "/6499/example/interstitial"
var idAppOpen = "/6499/example/app-open"


fun Context.initialize() {
    MobileAds.initialize(this)
    AudienceNetworkAds.initialize(this)
}

fun testDeviceIdsFacebook(ids: String) {
    AdSettings.addTestDevice(ids)
}
fun testDeviceIds(ids: List<String>) {
    val builder = RequestConfiguration.Builder().setTestDeviceIds(ids).build()
    MobileAds.setRequestConfiguration(builder)
}