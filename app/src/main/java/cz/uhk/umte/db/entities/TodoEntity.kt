package cz.uhk.umte.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class TodoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val text: String = "",
    val checked: Boolean = false,
    val date: String? = null, // Date
    val imageUri: String? = null,
)