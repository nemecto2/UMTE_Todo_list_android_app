package cz.uhk.umte.db.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = [
    ForeignKey(
        entity = TodoEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("todoId"),
        onDelete = ForeignKey.CASCADE,
    )
])
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val text: String = "",
    val todoId: Long? = null,
)
