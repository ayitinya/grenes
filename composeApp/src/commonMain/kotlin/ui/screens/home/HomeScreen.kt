package ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import moe.tlaster.precompose.koin.koinViewModel
import ui.catalog.PostCard

@Composable
fun HomeScreenUi(
    modifier: Modifier = Modifier, viewModel: HomeViewModel = koinViewModel(HomeViewModel::class)
) {
    HomeScreenUi(modifier)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreenUi(modifier: Modifier = Modifier) {
    Scaffold(modifier = modifier, topBar = {
        TopAppBar(title = { Text(text = "Feed") }, actions = {
            Row {
                IconButton(onClick = {}) {
                    Icon(Icons.Default.Settings, contentDescription = "Settings")
                }
            }
        })
    }) { innerPadding ->
        LazyColumn(contentPadding = innerPadding, modifier = Modifier.padding(horizontal = 8.dp)) {
            item {
                PostCard(modifier = Modifier.padding(vertical = 8.dp))
                Divider(modifier = Modifier.fillParentMaxWidth())
            }

            item {
                PostCard(modifier = Modifier.padding(vertical = 8.dp))
                Divider(modifier = Modifier.fillParentMaxWidth())
            }
            item {
                PostCard(modifier = Modifier.padding(vertical = 8.dp))
                Divider(modifier = Modifier.fillParentMaxWidth())
            }
            item {
                PostCard(modifier = Modifier.padding(vertical = 8.dp))
                Divider(modifier = Modifier.fillParentMaxWidth())
            }
            item {
                PostCard(modifier = Modifier.padding(vertical = 8.dp))
                Divider(modifier = Modifier.fillParentMaxWidth())
            }
        }
    }
}
