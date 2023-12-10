package ui.home

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import moe.tlaster.precompose.koin.koinViewModel

@Composable
fun HomeScreenUi(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel(HomeViewModel::class)
) {
    HomeScreenUi(modifier)
}

@Composable
private fun HomeScreenUi(modifier: Modifier = Modifier) {
    Scaffold { innerPadding ->
        Text(
            text = "Hello World",
            modifier = modifier
        )
    }
}