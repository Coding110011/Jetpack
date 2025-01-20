package com.example.tvreciapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.tvreciapp.data.model.Pokemon
import com.example.tvreciapp.data.model.RickAndMortyCharacter
import com.example.tvreciapp.ui.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    viewModel: MainViewModel,
    characterId: Int,
    isPokemon: Boolean,
    onBackClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isPokemon) "Pokemon Detail" else "Character Detail") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        when (uiState) {
            is MainViewModel.UiState.Success -> {
                val data = uiState as MainViewModel.UiState.Success
                if (isPokemon) {
                    val pokemon = data.pokemon.find { it.id == characterId }
                    pokemon?.let { PokemonDetailContent(it) }
                } else {
                    val character = data.rickAndMortyCharacters.find { it.id == characterId }
                    character?.let { CharacterDetailContent(it) }
                }
            }
            is MainViewModel.UiState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = (uiState as MainViewModel.UiState.Error).message,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
            MainViewModel.UiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
fun CharacterDetailContent(character: RickAndMortyCharacter) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        AsyncImage(
            model = character.image,
            contentDescription = character.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        DetailCard {
            DetailItem("Name", character.name)
            DetailItem("Status", character.status)
            DetailItem("Species", character.species)
            DetailItem("Gender", character.gender)
            DetailItem("Origin", character.origin.name)
            DetailItem("Location", character.location.name)
        }
    }
}

@Composable
fun PokemonDetailContent(pokemon: Pokemon) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Official Artwork
        pokemon.sprites.other?.officialArtwork?.frontDefault?.let { artworkUrl ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.secondaryContainer),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = artworkUrl,
                    contentDescription = "${pokemon.name} official artwork",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentScale = ContentScale.Fit
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Sprite Gallery
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    "Sprite Gallery",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    // Default Sprites
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        SpriteImage(pokemon.sprites.frontDefault, "Front Default")
                        SpriteImage(pokemon.sprites.backDefault, "Back Default")
                    }
                    
                    // Shiny Sprites
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        SpriteImage(pokemon.sprites.frontShiny, "Front Shiny")
                        SpriteImage(pokemon.sprites.backShiny, "Back Shiny")
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        DetailCard {
            DetailItem("Name", pokemon.name.capitalize())
            DetailItem("Height", "${pokemon.height/10.0} m")
            DetailItem("Weight", "${pokemon.weight/10.0} kg")
            DetailItem("Types", pokemon.types.joinToString { it.type.name.capitalize() })
            
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Base Stats",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            
            pokemon.stats.forEach { stat ->
                StatBar(
                    statName = stat.stat.name.replace("-", " ").capitalize(),
                    statValue = stat.baseStat
                )
            }
        }
    }
}

@Composable
fun SpriteImage(url: String, label: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = url,
                contentDescription = label,
                modifier = Modifier
                    .size(80.dp),
                contentScale = ContentScale.Fit
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun StatBar(statName: String, statValue: Int) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = statName,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = statValue.toString(),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
        Spacer(modifier = Modifier.height(2.dp))
        LinearProgressIndicator(
            progress = statValue / 255f,
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = when {
                statValue >= 150 -> Color(0xFF4CAF50) // High stats
                statValue >= 90 -> Color(0xFF2196F3) // Medium stats
                else -> Color(0xFFFF9800) // Low stats
            }
        )
    }
}

@Composable
fun DetailCard(content: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            content()
        }
    }
}

@Composable
fun DetailItem(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
