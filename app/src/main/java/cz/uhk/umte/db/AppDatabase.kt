package cz.uhk.umte.db

import androidx.room.Database
import androidx.room.RoomDatabase
import cz.uhk.umte.db.dao.NoteDao
import cz.uhk.umte.db.dao.TodoDao
import cz.uhk.umte.db.entities.NoteEntity
import cz.uhk.umte.db.entities.TodoEntity


@Database(
    version = AppDatabase.Version,
    entities = [
        TodoEntity::class,
        NoteEntity::class,
    ],
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun todoDao(): TodoDao

    companion object {
        const val Version = 1
        const val Name = "UmteTodoDatabase"
    }
}