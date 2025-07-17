package com.test.testcompose.ui.screen.landing

import android.annotation.SuppressLint
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.test.testcompose.R
import com.test.testcompose.ui.screen.landing.component.PokemonItemView
import com.test.testcompose.util.buildJsonFromPokemonItems

@SuppressLint("UnrememberedMutableState")
@Composable
fun LandingScreen(
    lazyGridState: LazyGridState,
    state: LandingState,
    onEvent: (LandingEvent) -> Unit,
    onNavigate: (id: String) -> Unit
) {
    val context = LocalContext.current
    val shouldLoadMore by derivedStateOf {
        val lastVisible = lazyGridState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
        lastVisible >= state.items.size - 1
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument("application/json"),
        onResult = { uri ->
            if (uri != null) {
                context.contentResolver.openOutputStream(uri)?.use { outputStream ->
                    val json = buildJsonFromPokemonItems(state.items) // build your JSON string
                    outputStream.write(json.toByteArray())
                }
            }
        }
    )


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
                        onItemClick = { name ->
                            onNavigate(name)
                        }
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

            Box(
                contentAlignment = Alignment.BottomEnd,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                IconButton(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .padding(4.dp)
                        .background(color = MaterialTheme.colorScheme.onSecondary),
                    onClick = { launcher.launch("pokemon_items_export.json") }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_export),
                        contentDescription = "Export"
                    )
                }
            }
        }
    }
}