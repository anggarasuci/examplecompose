package com.test.testcompose.ui.screen.detail

import com.test.testcompose.model.PokemonDetail

data class DetailState(
    val isLoading: Boolean = false,
    val data: PokemonDetail? = null,
    val error: String? = null,
)