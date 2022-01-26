package com.movify.mytrafficlight

import android.app.Application
import com.movify.mytrafficlight.di.myTrafficLightModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyTrafficLightApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@MyTrafficLightApplication)
            modules(
                myTrafficLightModule
            )
        }
    }
}