package com.example.tvreciapp.data.api

import com.example.tvreciapp.data.model.RickAndMortyResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RickAndMortyApi {
    @GET("character")
    fun getCharacters(@Query("page") page: Int = 1): Single<RickAndMortyResponse>
}
