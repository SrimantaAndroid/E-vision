package com.evision

import android.app.Application
import java.util.*

class EvisionApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val config = resources.configuration
        val locale = Locale("es")
        Locale.setDefault(locale)
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }
}