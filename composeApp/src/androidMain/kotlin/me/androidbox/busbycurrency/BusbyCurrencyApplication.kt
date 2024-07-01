package me.androidbox.busbycurrency

import android.app.Application
import di.initializeKoin

class BusbyCurrencyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initializeKoin {
            /** Use like this if you need an android context */
            // androidContext(this@BusbyCurrencyApplication)
        }
    }
}