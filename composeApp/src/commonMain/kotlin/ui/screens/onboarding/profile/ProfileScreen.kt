package ui.screens.onboarding.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowLeft
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import moe.tlaster.precompose.koin.koinViewModel

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileScreenViewModel = koinViewModel(ProfileScreenViewModel::class)
) {
    ProfileScreen(modifier)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProfileScreen(modifier: Modifier = Modifier) {
    Scaffold(modifier = modifier.padding(8.dp), topBar = {
        LargeTopAppBar(
            title = { Text("Profile") },
            navigationIcon = {
                IconButton(onClick = {}) {
                    Icon(imageVector = Icons.Default.ArrowLeft, contentDescription = "Back")
                }
            }
        )

    }) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().background(color = MaterialTheme.colorScheme.surface)
                .padding(paddingValues),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
               OutlinedTextField(
                        enabled = false,
                   value = "",
                     onValueChange = {},
                        label = { Text("Email") },
                        modifier = Modifier.fillMaxWidth()
               )

                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    label = { Text("What would you like to be called") },
                    modifier = Modifier.fillMaxWidth()
                )


                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    label = { Text("City") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    label = { Text("Country") },
                    modifier = Modifier.fillMaxWidth()
                )

            }

                Button(onClick = {}, ) {
                    Text("Save")}


        }
    }
}