package cz.uhk.umte.ui.components.dialogs

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable

@Composable
fun DeleteDialog(
    show: Boolean,
    onConfirm: ()->Unit,
    onDismiss: ()->Unit,
) {
    if (show) {
        AlertDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(
                    onClick = onConfirm
                ) {
                    Text(text = "Ano")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = onDismiss
                ) {
                    Text(text = "Ne")
                }
            },
            title = {
                Text(
                    text = "Opravdu chcete připomínku odstranit?"
                )
            }
        )
    }
}