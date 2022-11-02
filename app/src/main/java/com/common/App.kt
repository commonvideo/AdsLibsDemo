package com.common

import android.app.Application

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        AppOpenManager(this)
    }
}