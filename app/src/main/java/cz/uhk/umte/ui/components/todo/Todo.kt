package cz.uhk.umte.ui.components.todo

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
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

        Spacer(
            modifier = Modifier.width(8.dp),
        )

        Text(
            text = text,
            fontSize = 20.sp,
            modifier = Modifier.fillMaxWidth(0.84f),
        )

        Spacer(
            modifier = Modifier.width(8.dp),
        )

        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.padding(16.dp),
        ) {
            IconButton(
                modifier = Modifier.size(16.dp),
                onClick = handleDetail,
            ) {
                Image(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Detail",
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.primary),
                )
            }
        }
    }
}