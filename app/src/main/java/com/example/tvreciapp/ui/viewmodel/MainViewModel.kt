package com.example.tvreciapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.tvreciapp.data.model.Pokemon
import com.example.tvreciapp.data.model.RickAndMortyCharacter
import com.example.tvreciapp.data.repository.CharacterRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel(
    private val repository: CharacterRepository
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        fetchCharacters()
    }

    fun fetchCharacters() {
        _uiState.value = UiState.Loading
        repository.getCharacters()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ (rickAndMortyCharacters, pokemon) ->
                _uiState.value = UiState.Success(rickAndMortyCharacters, pokemon)
            }, { error ->
                _uiState.value = UiState.Error(error.message ?: "Unknown error occurred")
            })
            .addTo(compositeDisposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    sealed class UiState {
        object Loading : UiState()
        data class Success(
            val rickAndMortyCharacters: List<RickAndMortyCharacter>,
            val pokemon: List<Pokemon>
        ) : UiState()
        data class Error(val message: String) : UiState()
    }
}
