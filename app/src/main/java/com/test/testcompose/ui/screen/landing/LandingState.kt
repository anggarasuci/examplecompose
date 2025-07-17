package com.test.testcompose.ui.screen.landing

import com.test.testcompose.model.PokemonItem

data class LandingState(
    val isLoading: Boolean = false,
    val items: List<PokemonItem> = emptyList(),
    val error: String? = null,
    val currentOffset: Int = 0,
)