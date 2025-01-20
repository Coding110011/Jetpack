package com.example.tvreciapp.di

import com.example.tvreciapp.data.api.PokemonApi
import com.example.tvreciapp.data.api.RickAndMortyApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single { provideOkHttpClient() }
    single { provideRickAndMortyApi(get()) }
    single { providePokemonApi(get()) }
}

private fun provideOkHttpClient(): OkHttpClient {
    val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    return OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()
}

private fun provideRickAndMortyApi(okHttpClient: OkHttpClient): RickAndMortyApi {
    return Retrofit.Builder()
        .baseUrl("https://rickandmortyapi.com/api/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()
        .create(RickAndMortyApi::class.java)
}

private fun providePokemonApi(okHttpClient: OkHttpClient): PokemonApi {
    return Retrofit.Builder()
        .baseUrl("https://pokeapi.co/api/v2/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()
        .create(PokemonApi::class.java)
}
