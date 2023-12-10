package ui.screens.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import moe.tlaster.precompose.koin.koinViewModel

@Composable
fun RegisterScreenUi(
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel = koinViewModel(RegisterViewModel::class)
) {
    RegisterScreenUi(modifier)
}

@Composable
private fun RegisterScreenUi(modifier: Modifier = Modifier) {
    Column(modifier = modifier.background(color = MaterialTheme.colorScheme.primary)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            Text(
                text = "Hello World",
            )
        }

    }
}