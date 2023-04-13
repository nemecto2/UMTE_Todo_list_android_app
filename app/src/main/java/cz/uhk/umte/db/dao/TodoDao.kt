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

//    @Query("SELECT * FROM NoteEntity WHERE DATE(date) >= DATE('now') ORDER BY date")
//    fun selectAllFollowing(): Flow<List<TodoEntity>>

//    @Query("SELECT * FROM NoteEntity WHERE date IS NULL ORDER BY DATE(date)")
//    fun selectAllWithoutDate(): Flow<List<TodoEntity>>

//    @Query("SELECT * FROM NoteEntity WHERE date IS NOT NULL ORDER BY date")
//    fun selectAllWithDate(): Flow<List<TodoEntity>>

    @Query("SELECT * FROM TodoEntity")
    fun selectAll(): Flow<List<TodoEntity>>

    @Query("SELECT * FROM TodoEntity WHERE id=:id")
    fun selectById(id: Long): Flow<TodoEntity>
}