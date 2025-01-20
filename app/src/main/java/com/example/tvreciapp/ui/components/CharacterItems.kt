package com.example.tvreciapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.tvreciapp.data.model.Pokemon
import com.example.tvreciapp.data.model.RickAndMortyCharacter

@Composable
fun RickAndMortyCharacterItem(
    character: RickAndMortyCharacter,
    onClick: (RickAndMortyCharacter) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick(character) },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = character.image,
                contentDescription = character.name,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            
            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(1f)
            ) {
                Text(
                    text = character.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "${character.species} - ${character.status}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Location: ${character.location.name}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
fun PokemonItem(
    pokemon: Pokemon,
    onClick: (Pokemon) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick(pokemon) },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = pokemon.sprites.frontDefault,
                contentDescription = pokemon.name,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            
            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(1f)
            ) {
                Text(
                    text = pokemon.name.capitalize(),
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Types: ${pokemon.types.joinToString { it.type.name }}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Height: ${pokemon.height/10.0}m, Weight: ${pokemon.weight/10.0}kg",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
