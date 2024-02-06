package ui.screens.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import moe.tlaster.precompose.koin.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun UserScreen(
    uid: String,
    modifier: Modifier = Modifier,
    viewModel: ProfileScreenViewModel = koinViewModel(ProfileScreenViewModel::class) { parametersOf(uid) }
) {
    val uiState by viewModel.uiState.collectAsState()

    when (uiState.loadingState) {
        LoadingState.Loading -> Loading(modifier = modifier)

        LoadingState.Success -> UserScreen(
            modifier = modifier,
            isOwnUser = uiState.isOwnUser,
            username = uiState.user?.username ?: "",
            displayName = uiState.user?.displayName ?: ""
        )

        is LoadingState.Error -> ErrorScreen(modifier = modifier)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun UserScreen(modifier: Modifier, isOwnUser: Boolean, username: String, displayName: String) {
    Scaffold(topBar = {
        TopAppBar(title = {
            Text(text = "@$username", style = MaterialTheme.typography.titleLarge)
        }, actions = {
            if (isOwnUser) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Profile"
                    )
                }
            }

            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Share Profile"
                )
            }
        })
    }, modifier = modifier) { paddingValues ->
        LazyColumn(contentPadding = paddingValues, modifier = Modifier.fillMaxSize()) {
            item {
                Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {

                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier.size(60.dp).clip(CircleShape)
                                .background(MaterialTheme.colorScheme.secondary)
                        )

                        Column {
                            Text(text = displayName, style = MaterialTheme.typography.headlineSmall)
                            Text(text = "@$username", style = MaterialTheme.typography.bodyMedium)
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth().padding(8.dp).clip(shape = RoundedCornerShape(16.dp))
                            .background(MaterialTheme.colorScheme.tertiaryContainer).padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Default.Task,
                                contentDescription = "Challenges",
                                modifier = Modifier.size(36.dp)
                            )
                            Text(text = "500", style = MaterialTheme.typography.titleMedium)
                            Text(text = "Challenges", style = MaterialTheme.typography.bodySmall)
                        }

                        Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Default.Score,
                                contentDescription = "Points Earned",
                                modifier = Modifier.size(36.dp)

                            )
                            Text(text = "500", style = MaterialTheme.typography.titleMedium)
                            Text(text = "Points Earned", style = MaterialTheme.typography.bodySmall)
                        }

                        Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Default.CalendarMonth,
                                contentDescription = "Streak",
                                modifier = Modifier.size(36.dp)
                            )
                            Text(text = "500", style = MaterialTheme.typography.titleMedium)
                            Text(text = "Days Streak", style = MaterialTheme.typography.bodySmall)
                        }
                    }

                    var state by remember { mutableStateOf(0) }
                    val titles = listOf("Submissions", "Timeline", "Gallery")

                    TabRow(
                        selectedTabIndex = state,
                    ) {
                        titles.forEachIndexed { index, title ->
                            Tab(
                                selected = state == index,
                                onClick = { state = index },
                                text = { Text(title) }
                            )
                        }
                    }

                    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                        when (state) {
                            0 -> {
                                Text(text = "Tab 1")
                            }

                            1 -> {
                                Text(text = "Tab 2")
                            }

                            2 -> {
                                Text(text = "Tab 3")
                            }
                        }
                    }


                }
            }
        }
    }
}


@Composable
internal fun Loading(modifier: Modifier) {
    Scaffold(modifier = modifier.then(Modifier.fillMaxSize())) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Loading")
        }
    }
}

@Composable
internal fun ErrorScreen(modifier: Modifier) {
    Scaffold(modifier = modifier.then(Modifier.fillMaxSize())) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Error, please try again later")
        }
    }
}