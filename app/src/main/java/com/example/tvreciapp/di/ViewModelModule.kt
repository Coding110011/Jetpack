package com.example.tvreciapp.di

import com.example.tvreciapp.data.repository.CharacterRepository
import com.example.tvreciapp.ui.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    single { CharacterRepository(get(), get()) }
    viewModel { MainViewModel(get()) }
}
