package com.example.tvreciapp.data.model

import com.google.gson.annotations.SerializedName

data class Pokemon(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val sprites: Sprites,
    val types: List<TypeSlot>,
    val stats: List<Stat>
)

data class Sprites(
    @SerializedName("front_default")
    val frontDefault: String,
    @SerializedName("back_default")
    val backDefault: String,
    @SerializedName("front_shiny")
    val frontShiny: String,
    @SerializedName("back_shiny")
    val backShiny: String,
    val other: OtherSprites
)

data class OtherSprites(
    @SerializedName("official-artwork")
    val officialArtwork: OfficialArtwork
)

data class OfficialArtwork(
    @SerializedName("front_default")
    val frontDefault: String?,
    @SerializedName("front_shiny")
    val frontShiny: String?
)

data class TypeSlot(
    val slot: Int,
    val type: Type
)

data class Type(
    val name: String,
    val url: String
)

data class Stat(
    @SerializedName("base_stat")
    val baseStat: Int,
    val effort: Int,
    val stat: StatInfo
)

data class StatInfo(
    val name: String,
    val url: String
)

data class PokemonListResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonListItem>
)

data class PokemonListItem(
    val name: String,
    val url: String
)
