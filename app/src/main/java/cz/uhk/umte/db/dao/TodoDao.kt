package cz.uhk.umte.db.dao

import androidx.room.*
import cz.uhk.umte.db.entities.TodoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(todo: TodoEntity)

    @Delete
    fun delete(todo: TodoEntity)

//    @Query("SELECT * FROM NoteEntity ORDER BY date WHERE DATE(date) >= DATE('now')")
//    fun selectAllFollowing(): Flow<List<TodoEntity>>

//    @Query("SELECT * FROM NoteEntity WHERE date IS NULL")
//    fun selectAllWithoutDate(): Flow<List<TodoEntity>>

//    @Query("SELECT * FROM NoteEntity WHERE date IS NOT NULL ORDER BY date")
//    fun selectAllWithDate(): Flow<List<TodoEntity>>

    @Query("SELECT * FROM TodoEntity")
    fun selectAll(): Flow<List<TodoEntity>>
}