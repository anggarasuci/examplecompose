package com.test.testcompose.ui.screen.landing

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.test.testcompose.ui.screen.landing.component.PokemonItemView

@SuppressLint("UnrememberedMutableState")
@Composable
fun LandingScreen(
    lazyGridState: LazyGridState,
    state: LandingState,
    onEvent: (LandingEvent) -> Unit,
    onNavigate: (id: String) -> Unit
) {

    val shouldLoadMore by derivedStateOf {
        val lastVisible = lazyGridState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
        lastVisible >= state.items.size - 1
    }

    LaunchedEffect(shouldLoadMore, state.isLoading) {
        if (shouldLoadMore && !state.isLoading) {
            onEvent(LandingEvent.LoadMore)
        }
    }

    Scaffold { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            LazyVerticalGrid(
                state = lazyGridState,
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(state.items) {
                    PokemonItemView(
                        item = it,
                        onItemClick = { name -> onNavigate(name) }
                    )
                }
            }

            Box(
                contentAlignment = Center,
                modifier = Modifier.fillMaxSize()
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
            }
        }
    }
}