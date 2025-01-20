package com.example.tvreciapp.data.api

import com.example.tvreciapp.data.model.Pokemon
import com.example.tvreciapp.data.model.PokemonListResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {
    @GET("pokemon")
    fun getPokemonList(@Query("offset") offset: Int = 0, @Query("limit") limit: Int = 20): Single<PokemonListResponse>

    @GET("pokemon/{id}")
    fun getPokemonDetails(@Path("id") id: Int): Single<Pokemon>
}
