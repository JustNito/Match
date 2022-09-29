package com.smallgames.match.di

import android.content.Context
import com.smallgames.match.MainActivity
import dagger.BindsInstance
import dagger.Component

@Component(modules = [DataModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(activity: MainActivity)
}