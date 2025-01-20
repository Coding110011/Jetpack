package com.example.tvreciapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tvreciapp.ui.components.PokemonItem
import com.example.tvreciapp.ui.components.RickAndMortyCharacterItem
import com.example.tvreciapp.ui.components.ShimmerItem
import com.example.tvreciapp.ui.viewmodel.MainViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel = koinViewModel(),
    onCharacterClick: (Int, Boolean) -> Unit
) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Characters & Pokemon") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            TabRow(selectedTabIndex = selectedTabIndex) {
                Tab(
                    selected = selectedTabIndex == 0,
                    onClick = { selectedTabIndex = 0 },
                    text = { Text("Rick & Morty") }
                )
                Tab(
                    selected = selectedTabIndex == 1,
                    onClick = { selectedTabIndex = 1 },
                    text = { Text("Pokemon") }
                )
            }

            when (uiState) {
                is MainViewModel.UiState.Loading -> {
                    LazyColumn {
                        items(10) {
                            ShimmerItem()
                        }
                    }
                }
                
                is MainViewModel.UiState.Success -> {
                    val data = uiState as MainViewModel.UiState.Success
                    
                    when (selectedTabIndex) {
                        0 -> {
                            LazyColumn {
                                items(data.rickAndMortyCharacters) { character ->
                                    RickAndMortyCharacterItem(
                                        character = character,
                                        onClick = { onCharacterClick(it.id, false) }
                                    )
                                }
                            }
                        }
                        1 -> {
                            LazyColumn {
                                items(data.pokemon) { pokemon ->
                                    PokemonItem(
                                        pokemon = pokemon,
                                        onClick = { onCharacterClick(it.id, true) }
                                    )
                                }
                            }
                        }
                    }
                }
                
                is MainViewModel.UiState.Error -> {
                    val error = uiState as MainViewModel.UiState.Error
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = error.message,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}
