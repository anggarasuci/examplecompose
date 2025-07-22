package com.test.testcompose.ui.screen.user.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.test.testcompose.ui.component.CustomCard
import com.test.testcompose.ui.component.ImageWithLoading
import com.test.testcompose.ui.screen.user.detail.component.LabelValue
import com.test.testcompose.ui.screen.user.detail.component.Section

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailScreen(
    id: Int,
    state: UserDetailState,
    onEvent: (UserDetailEvent) -> Unit,
    onBack: () -> Unit
) {
    LaunchedEffect(true) {
        onEvent(UserDetailEvent.OnLoad(id))
    }

    Scaffold(topBar = {
        TopAppBar(
            title = {}, navigationIcon = {
                IconButton(onClick = { onBack() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            }, colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.LightGray
            )
        )
    }) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.LightGray)
                .padding(padding)
        ) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                item {
                    CustomCard(modifier = Modifier.fillMaxWidth()) {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            if (state.data.image.isNotBlank()) {
                                Card(modifier = Modifier.size(68.dp)) {
                                    ImageWithLoading(
                                        url = state.data.image,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(64.dp)
                                    )
                                }
                            }

                            Column(modifier = Modifier.padding(start = 16.dp)) {
                                Text(
                                    state.data.username,
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(
                                    "${state.data.firstName} ${state.data.lastName}",
                                    style = MaterialTheme.typography.headlineSmall
                                )
                            }
                        }
                    }
                }

                item {
                    CustomCard(modifier = Modifier.fillMaxWidth()) {
                        Column {
                            Text("Contact", style = MaterialTheme.typography.headlineSmall)
                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Email,
                                    contentDescription = "email"
                                )
                                Text(
                                    state.data.email,
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.padding(start = 8.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Call,
                                    contentDescription = "phone"
                                )
                                Text(
                                    state.data.phone,
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.padding(start = 8.dp)
                                )
                            }
                        }


                    }
                }

                item {
                    Section(title = "Address") {
                        LabelValue(label = "Street", value = state.data.address.address)
                        LabelValue(label = "City", value = state.data.address.city)
                        LabelValue(label = "State", value = state.data.address.state)
                        LabelValue(label = "Country", value = state.data.address.country)
                        LabelValue(label = "Postal Code", value = state.data.address.postalCode)
                    }
                }

                item {
                    Section(title = "Company") {
                        LabelValue(label = "Title", value = state.data.company.title)
                        LabelValue(label = "Department", value = state.data.company.department)
                        LabelValue(label = "Name", value = state.data.company.name)
                    }
                }

                item {
                    Section(title = "Identity") {
                        LabelValue(label = "Age", value = state.data.age.toString() + " years")
                        LabelValue(label = "Gender", value = state.data.gender)
                        LabelValue(label = "Birthdate", value = state.data.birthDate)
                        LabelValue(label = "Height", value = state.data.height.toString() + " cm")
                        LabelValue(label = "Weight", value = state.data.weight.toString() + " kg")
                        LabelValue(label = "Eye Color", value = state.data.eyeColor)
                    }
                }
            }
        }
    }
}