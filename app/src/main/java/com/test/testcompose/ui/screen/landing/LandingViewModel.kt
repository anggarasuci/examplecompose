package com.test.testcompose.ui.screen.landing

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.testcompose.repository.PokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LandingViewModel(
    private val repository: PokemonRepository
) : ViewModel() {
    private val _pageSize = 10
    var state by mutableStateOf(LandingState())
        private set

    fun onEvent(event: LandingEvent) {
        when (event) {
            is LandingEvent.LoadMore -> loadData(state.currentOffset + _pageSize)
        }
    }

    init {
        loadData(0)
    }

    private fun loadData(offset: Int) {
        state = state.copy(isLoading = true, error = null)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val items = repository.getList(offset, _pageSize)
                state = state.copy(
                    items = (if (offset == 0) emptyList() else state.items) + items,
                    currentOffset = offset
                )
            } catch (e: Exception) {
                state = state.copy(error = e.message)
            }
        }
        state = state.copy(isLoading = false)
    }
}