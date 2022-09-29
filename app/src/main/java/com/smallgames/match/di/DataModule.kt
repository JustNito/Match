package com.smallgames.match.di

import com.smallgames.match.data.Preferences
import dagger.Binds
import dagger.Module
import com.smallgames.match.data.Storage

@Module
abstract class DataModule {

    @Binds
    abstract fun provideLocalStorage(storage: Preferences): Storage
}