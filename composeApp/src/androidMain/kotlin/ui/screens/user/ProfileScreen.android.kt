package ui.screens.user

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun UserScreenPreview() {
    UserScreen(modifier = Modifier, isOwnUser = true, displayName = "Rudy Ayitinya", username = "ayitinya")
}

@Preview
@Composable
fun LoadingPreview() {
    Loading(modifier = Modifier)
}
