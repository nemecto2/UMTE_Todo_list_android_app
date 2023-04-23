package cz.uhk.umte.db.dao

import androidx.room.*
import cz.uhk.umte.db.entities.NoteEntity
import cz.uhk.umte.db.entities.TodoEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(note: NoteEntity)

    @Delete
    fun delete(note: NoteEntity)

    @Query("DELETE FROM NoteEntity WHERE todoId=:id")
    fun deleteByTodoId(id: Long)

    @Query("SELECT * FROM NoteEntity WHERE todoId=:id")
    fun selectByTodoId(id: Long): Flow<NoteEntity>

    @Query("SELECT * FROM NoteEntity")
    fun selectAll(): Flow<List<NoteEntity>>
}