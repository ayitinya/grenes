package ui.screens.challengedetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import moe.tlaster.precompose.koin.koinViewModel
import org.koin.core.parameter.parametersOf
import ui.screens.user.Loading

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChallengeDetailScreen(
    uid: String,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ChallengeDetailViewModel = koinViewModel(ChallengeDetailViewModel::class) { parametersOf(uid) }
) {
    val uiState by viewModel.uiState.collectAsState()

    when (uiState.loading) {
        true -> Loading(modifier = modifier)

        false -> {
            var moreDropdownMenuOpen by remember { mutableStateOf(false) }



            Scaffold(
                topBar = {

                    TopAppBar(
                        navigationIcon = {
                            IconButton(onClick = navigateBack) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = "Back"
                                )
                            }
                        },
                        actions = {
                            IconButton(onClick = {}) {
                                Icon(
                                    imageVector = Icons.Default.Share,
                                    contentDescription = "Share"
                                )
                            }

                            Box {
                                IconButton(onClick = { moreDropdownMenuOpen = true }) {
                                    Icon(
                                        imageVector = Icons.Default.MoreVert,
                                        contentDescription = "More"
                                    )
                                }

                                DropdownMenu(
                                    expanded = moreDropdownMenuOpen,
                                    onDismissRequest = { moreDropdownMenuOpen = false },

                                    ) {
                                    DropdownMenuItem(text = { Text("Report a problem") }, onClick = {})
                                }
                            }

                        },
                        title = {
                            Text(text = "Challenge Detail", style = MaterialTheme.typography.titleLarge)
                        }
                    )
                },
                floatingActionButton = {
                    ExtendedFloatingActionButton(onClick = {}) {
                        Text(text = "Make A Submission")
                    }
                }
            ) { paddingValues ->
                Column(
                    modifier = Modifier.padding(paddingValues).padding(horizontal = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = uiState.challenge?.title ?: "No name available",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(text = uiState.challenge?.description ?: "No description available")
                }
            }
        }
    }
}