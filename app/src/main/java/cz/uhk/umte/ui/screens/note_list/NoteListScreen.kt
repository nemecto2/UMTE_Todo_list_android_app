package cz.uhk.umte.ui.screens.note_list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.koin.androidx.compose.getViewModel

@Composable
fun NoteListScreen(
    controller: NavHostController,
    viewModel: NoteListVM = getViewModel(),
) {
    // Vytažení všech TODOS z VM
    val notes = viewModel.notes.collectAsState(emptyList())

    // Sestavení pohledu
    Column {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1F),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(16.dp),
        ) {
            items(
                items = notes.value,
                key = { note -> note.id },
            ) { note ->
                Text(
                   text = note.text
                )

                Text(
                    text = "${note.todoId}",
                    color = MaterialTheme.colors.secondary,
                )

                Divider(
                    color = MaterialTheme.colors.secondary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                )
            }
        }
    }
}