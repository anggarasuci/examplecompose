package com.test.testcompose.ui.screen.user.landing

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.testcompose.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserLandingViewModel(private val repository: UserRepository) : ViewModel() {
    var state by mutableStateOf(UserLandingState())
        private set

    fun onEvent(event: UserLandingEvent) {
        when (event) {
            is UserLandingEvent.OnSearch -> {
                handleOnSearch(event.keyword)
            }
        }
    }

    init {
        loadData()
    }

    private fun handleOnSearch(keyword: String?) {
        state = state.copy(keyword = keyword ?: "")
        when {
            keyword.isNullOrEmpty() -> loadData()
            keyword.length >= 2 -> loadData(keyword)
        }
    }

    private fun loadData(keyword: String? = null) {
        state = state.copy(isLoading = true, error = null)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val users = repository.getList(keyword)

                state = state.copy(users = users)
            } catch (e: Exception) {
                state = state.copy(error = e.message)
            }
        }
        state = state.copy(isLoading = false)
    }
}