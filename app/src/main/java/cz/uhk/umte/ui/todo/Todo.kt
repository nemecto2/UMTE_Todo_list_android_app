package cz.uhk.umte.ui.todo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cz.uhk.umte.db.dao.NoteDao
import cz.uhk.umte.db.dao.TodoDao
import cz.uhk.umte.db.entities.TodoEntity

@Composable
fun Todo(
    text: String,
    checked: Boolean,

    handleChecked: (Boolean)->Unit,
    handleDetail: ()->Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(
            modifier = Modifier.size(32.dp),
            checked = checked,
            onCheckedChange = handleChecked,
        )
        Text(
            text = text,
            fontSize = 20.sp
        )
        IconButton(
            modifier = Modifier.size(16.dp),
            onClick = handleDetail,
        ) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Detail",
            )
        }
    }
}