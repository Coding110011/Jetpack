package com.example.tvreciapp.data.repository

import com.example.tvreciapp.data.api.PokemonApi
import com.example.tvreciapp.data.api.RickAndMortyApi
import com.example.tvreciapp.data.model.Pokemon
import com.example.tvreciapp.data.model.RickAndMortyCharacter
import com.example.tvreciapp.data.model.Sprites
import com.example.tvreciapp.data.model.OtherSprites
import com.example.tvreciapp.data.model.OfficialArtwork
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.functions.BiFunction

class CharacterRepository(
    private val rickAndMortyApi: RickAndMortyApi,
    private val pokemonApi: PokemonApi
) {
    fun getCharacters(): Single<Pair<List<RickAndMortyCharacter>, List<Pokemon>>> {
        val rickAndMortyCharacters = rickAndMortyApi.getCharacters()
            .map { it.results }
            .onErrorReturn { emptyList() }

        val pokemonList = pokemonApi.getPokemonList()
            .flatMap { response ->
                val pokemonIds = response.results.take(20).mapIndexed { index, _ -> index + 1 }
                val pokemonDetails = pokemonIds.map { pokemonId -> 
                    pokemonApi.getPokemonDetails(pokemonId)
                        .onErrorReturn { 
                            // Return a default Pokemon object in case of error
                            Pokemon(
                                id = pokemonId, // Use the pokemonId parameter here
                                name = "Unknown Pokemon #$pokemonId",
                                height = 0,
                                weight = 0,
                                sprites = Sprites(
                                    frontDefault = "",
                                    backDefault = "",
                                    frontShiny = "",
                                    backShiny = "",
                                    other = OtherSprites(
                                        officialArtwork = OfficialArtwork(
                                            frontDefault = null,
                                            frontShiny = null
                                        )
                                    )
                                ),
                                types = emptyList(),
                                stats = emptyList()
                            )
                        }
                }
                Single.zip(pokemonDetails) { pokemonArray ->
                    pokemonArray.map { it as Pokemon }
                }
            }
            .onErrorReturn { emptyList() }

        return Single.zip(
            rickAndMortyCharacters,
            pokemonList,
            BiFunction { characters, pokemon ->
                Pair(characters, pokemon)
            }
        )
    }
}
