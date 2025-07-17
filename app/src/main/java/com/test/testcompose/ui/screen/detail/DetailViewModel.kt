package com.test.testcompose.ui.screen.detail


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.testcompose.repository.PokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: PokemonRepository
) : ViewModel() {
    var state by mutableStateOf(DetailState())
        private set

    fun onEvent(event: DetailEvent) {
        when (event) {
            is DetailEvent.Init -> loadData(event.name)
        }
    }

    private fun loadData(name: String) {
        state = state.copy(isLoading = true, error = null)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repository.getDetail(name)
                state = state.copy(
                    data = result
                )
            } catch (e: Exception) {
                state = state.copy(error = e.message)
            }
        }
        state = state.copy(isLoading = false)
    }
}