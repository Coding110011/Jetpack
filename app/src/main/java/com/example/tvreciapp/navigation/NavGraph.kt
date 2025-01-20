package com.example.tvreciapp.navigation

sealed class Screen(val route: String) {
    object Main : Screen("main")
    object RickAndMortyDetail : Screen("rickandmorty/{id}") {
        fun createRoute(id: Int) = "rickandmorty/$id"
    }
    object PokemonDetail : Screen("pokemon/{id}") {
        fun createRoute(id: Int) = "pokemon/$id"
    }
}

sealed class DetailType {
    data class RickAndMorty(val id: Int) : DetailType()
    data class Pokemon(val id: Int) : DetailType()
}
