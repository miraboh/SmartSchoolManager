package com.mobileedu02.smartschoolmanager

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SmartSchApplication: Application()  {
    override fun onCreate() {
        super.onCreate()
    }
}