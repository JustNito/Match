package com.smallgames.match

import android.app.Application
import com.smallgames.match.di.DaggerAppComponent

class MyApp : Application() {
    val appComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }
}