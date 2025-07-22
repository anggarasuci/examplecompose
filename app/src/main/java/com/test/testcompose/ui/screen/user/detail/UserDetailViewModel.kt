package com.test.testcompose.ui.screen.user.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.testcompose.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class USerDetailViewModel(
    private val repository: UserRepository
) : ViewModel() {
    var state by mutableStateOf(UserDetailState())
        private set

    fun onEvent(event: UserDetailEvent) {
        when (event) {
            is UserDetailEvent.OnLoad -> loadData(event.id)
        }
    }

    private fun loadData(id: Int) {
        state = state.copy(isLoading = true, error = null)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repository.getDetail(id)
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