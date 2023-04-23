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

    @Query("DELETE FROM TodoEntity WHERE `date` IS NOT NULL AND DATE(`date`) < DATE('now')")
    fun deleteOld()

    @Query("SELECT * FROM TodoEntity WHERE `date` IS NOT NULL AND DATE(`date`) >= DATE('now') ORDER BY `date`")
    fun selectAllFollowing(): Flow<List<TodoEntity>>

    @Query("SELECT * FROM TodoEntity WHERE `date` IS NULL")
    fun selectAllWithoutDate(): Flow<List<TodoEntity>>

    @Query("SELECT * FROM TodoEntity")
    fun selectAll(): Flow<List<TodoEntity>>

    @Query("SELECT * FROM TodoEntity WHERE id=:id")
    fun selectById(id: Long): Flow<TodoEntity>

    @Query("SELECT * FROM TodoEntity WHERE `date` IS NOT NULL AND DATE(`date`) = DATE('now')")
    fun selectToday(): Flow<List<TodoEntity>>
}