package com.example.tvreciapp.data.model

data class RickAndMortyCharacter(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val image: String,
    val url: String,
    val created: String,
    val origin: Origin,
    val location: Location
)

data class Origin(
    val name: String,
    val url: String
)

data class Location(
    val name: String,
    val url: String
)

data class RickAndMortyResponse(
    val info: PageInfo,
    val results: List<RickAndMortyCharacter>
)

data class PageInfo(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)
