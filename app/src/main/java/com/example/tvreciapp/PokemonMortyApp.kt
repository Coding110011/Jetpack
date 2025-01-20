package com.example.tvreciapp

import android.app.Application
import com.example.tvreciapp.di.networkModule
import com.example.tvreciapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class PokemonMortyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        
        startKoin {
            androidLogger()
            androidContext(this@PokemonMortyApp)
            modules(listOf(networkModule, viewModelModule))
        }
    }
}
